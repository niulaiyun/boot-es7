package com.nly.base.service.impl;


import com.nly.base.service.IBaseESService;
import com.nly.util.PageBean;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program:boot-es7
 * @author: nly
 */
public  abstract class BaseESService <T, ID> implements IBaseESService<T, ID> {

    public abstract T createEntity();

    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;

    @SuppressWarnings("unchecked")
    @Override
    public T get(ID entityId) {
        return (T) elasticsearchRestTemplate.get(String.valueOf(entityId), createEntity().getClass());
    }

    @SuppressWarnings("deprecation")
    @Override
    public T save(@RequestBody T entity) {
        // ??????????????????????????????????????????mapping
        boolean bool = elasticsearchRestTemplate.indexExists(createEntity().getClass());
        if (!bool) {
            // ????????????mapping ????????????????????????mapping???????????????????????????????????????????????? ??????????????????????????????mapping
            elasticsearchRestTemplate.createIndex(createEntity().getClass());
            elasticsearchRestTemplate.putMapping(createEntity().getClass());
        }
        T save = elasticsearchRestTemplate.save(entity);
        return save;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T update(T entity) throws Exception {
        Class<? extends Object> class1 = entity.getClass();
        Field[] fields = class1.getDeclaredFields();
        Document document = Document.create();
        String id = "";
        for (Field field : fields) {
            Object value = null;
            if (field.isAnnotationPresent(Id.class)) {
                id = (String) field.get(entity);
            } else if (field.isAnnotationPresent(org.springframework.data.elasticsearch.annotations.Field.class)) {
                value = field.get(entity);
                if (!ObjectUtils.isEmpty(value)) {
                    document.put(field.getName(), value);
                }

            }
        }
        elasticsearchRestTemplate.update(UpdateQuery.builder(id).withDocument(document).build(),
                getIndexName());
        ID entityId = (ID) id;
        entity = get(entityId);
        return entity;
    }

    @Override
    public T delete(ID entityId) {
        T t = this.get(entityId);
        elasticsearchRestTemplate.delete(String.valueOf(entityId), createEntity().getClass());
        return t;
    }

    /**
     * ???????????????
     *
     * @return
     */
    public IndexCoordinates getIndexName() {
        Class<? extends Object> class1 = createEntity().getClass();
        String indexName = "";
        if (class1.isAnnotationPresent(org.springframework.data.elasticsearch.annotations.Document.class)) {
            org.springframework.data.elasticsearch.annotations.Document annotation = class1
                    .getAnnotation(org.springframework.data.elasticsearch.annotations.Document.class);
            indexName = annotation.indexName();
        }
        return IndexCoordinates.of(indexName);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> selectCombox(@RequestBody PageBean pageBean) throws Exception {
        NativeSearchQuery searchQuery = queryNativeSearchQuery(pageBean, Boolean.FALSE);
        @SuppressWarnings("deprecation")
        List<? extends Object> queryForList = elasticsearchRestTemplate.queryForList(searchQuery,
                createEntity().getClass(), getIndexName());
        return (List<T>) queryForList;
    }

    /**
     * ????????????
     * @param pageBean
     * @param bool
     * @return
     */
    public NativeSearchQuery queryNativeSearchQuery(PageBean pageBean, Boolean bool) {
        // ??????
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // ????????????
        Map<String, String> params = pageBean.getParams();
        // ??????
        String[][] sorts = pageBean.getSort();
        // ????????????
        String[] keywords = pageBean.getKeywords();
        // ????????????
        String[][] ranges = pageBean.getRanges();
        // ?????????
        String keyword1 = pageBean.getKeyword();
        // ?????????????????????
        String[] highlightwords = pageBean.getHighlightwords();
        if (ranges != null && ranges.length > 0) {
            // ????????????
            for (String[] line : ranges) {
                RangeQueryBuilder range = new RangeQueryBuilder(line[0]);
                // ???????????????????????????
                if (line.length > 2) {
                    if (line[2].length() == 10) {
                        String endDate = String.format("%s 23:59:59", line[2]);
                        range.lte(endDate);
                    } else {
                        range.lte(line[2]);
                    }
                    range.gte(line[1]);
                } else if (line.length == 2) {
                    range.gte(line[1]);
                }

                boolQueryBuilder.must(range);
            }
        }
        if (!ObjectUtils.isEmpty(params)) {
            // ????????????
            params.forEach((k, v) -> {
                boolQueryBuilder.must(new MatchQueryBuilder(k, v));

            });
        }
        // ???????????????
        if (StringUtils.isNotBlank(keyword1)) {
            for (String keyword : keywords) {
                /*
                 * should(QueryBuilders.matchQuery("xm","??????"))//???????????????
                 * .should(QueryBuilders.matchParaseQuery("addr","?????????"))//???????????????
                 * .should(QueryBuilders.termQuery("status",0))//????????????
                 * .should(QueryBuilders.termsQuery("keyword",string[]))//??????????????????
                 */
                // ?????????
                boolQueryBuilder.should(new MatchQueryBuilder(keyword, keyword1));
            }
        }

        // ????????????
        PageRequest pageRequest = PageRequest.of(pageBean.getPage() - 1, pageBean.getPagerows());
        // ??????
        SortBuilder<?> sortBuilder = SortBuilders.scoreSort();
        if (sorts != null && sorts.length > 0) {
            for (String[] sort : sorts) {
                sortBuilder = SortBuilders.fieldSort(sort[0]);
                if (StringUtils.isBlank(sort[1]) || "asc".equals(sort[1])) {
                    sortBuilder.order(SortOrder.ASC);
                } else {
                    sortBuilder.order(SortOrder.DESC);
                }

            }
        }
        // ????????????
        HighlightBuilder highlightBuilder = new HighlightBuilder();

        if (highlightwords != null && highlightwords.length > 0) {
            // ????????????
            for (String highlightword : highlightwords) {
                highlightBuilder.field(highlightword);
            }
            highlightBuilder.preTags("<font color='red'>").postTags("</font>");
        }
        // ????????????
        NativeSearchQuery searchQuery = null;

        if (bool) {
            searchQuery = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder)// ??????
                    .withPageable(pageRequest)// ????????????
                    .withSort(sortBuilder)// ??????
                    .withHighlightBuilder(highlightBuilder)// ????????????
                    .build();
        } else {
            searchQuery = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder)// ??????
                    .withSort(sortBuilder)// ??????
                    .withHighlightBuilder(highlightBuilder)// ????????????
                    .build();
        }

        return searchQuery;
    }

    @Override
    public PageBean search(PageBean pageBean) throws Exception, Exception {
        String[] highlightwords = pageBean.getHighlightwords();
        NativeSearchQuery searchQuery = queryNativeSearchQuery(pageBean, Boolean.TRUE);
        SearchHits<? extends Object> search = elasticsearchRestTemplate.search(searchQuery, createEntity().getClass());
        List<Object> rows = new ArrayList<>();
        // ????????????
        for (SearchHit<? extends Object> s : search) {
            Object content = s.getContent();
            // ???????????????????????????
            if (highlightwords != null && highlightwords.length > 0) {
                for (String highlightword : highlightwords) {
                    List<String> highlightField = s.getHighlightField(highlightword);
                    if (highlightField.size() > 0) {
                        Field field = content.getClass().getDeclaredField(highlightword);
                        field.setAccessible(true);
                        field.set(content, highlightField.get(0));
                    }
                }

            }
            rows.add(content);
        }

        long count = elasticsearchRestTemplate.count(searchQuery, createEntity().getClass());
        pageBean.setCount(count);
        pageBean = new PageBean(rows, count);

        return pageBean;
    }

    @Override
    public Object aggregationCount() {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.addAggregation(AggregationBuilders.terms("brand").field("brand"));
        AggregatedPage<? extends Object> queryForPage = elasticsearchRestTemplate.queryForPage(queryBuilder.build(),
                createEntity().getClass(), getIndexName());
        Map<String, Aggregation> asMap = queryForPage.getAggregations().asMap();

        ParsedStringTerms aggregation = (ParsedStringTerms) asMap.get("brand");
        List<? extends org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket> buckets = aggregation.getBuckets();
        for (org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket bucket : buckets) {
            long docCount = bucket.getDocCount();
            System.out.println(docCount);
            Aggregations aggregations = bucket.getAggregations();
            Aggregation aggregation2 = aggregations.get("brand");
            System.out.println(aggregation2);
        }
        //System.out.println(buckets);

        return buckets;
    }
}
