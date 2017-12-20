package com.minyou.manba.network.resultModel;

import java.util.List;

/**
 * Created by luchunhao on 2017/12/19.
 */
public class ZoneListResultModel extends BaseResultModel {

    /**
     * pageNo : 1
     * pageSize : 10
     * maxPageSize : 100
     * totalCount : 10
     * resultList : [{"id":1,"userId":1,"zoneContent":"一个十年门户老编的哀叹：被今日头条革了命","zoneImage":"www.baidu.com/1.jpg","publishTime":1506666888000,"yn":0,"updateTime":null},{"id":2,"userId":2,"zoneContent":"被赔\u201c葱\u201d的宾利车主现身 称赔\u201c葱\u201d大爷有点儿意思","zoneImage":"www.baidu.com/2.jpg","publishTime":1506666929000,"yn":0,"updateTime":null},{"id":3,"userId":3,"zoneContent":"皇马续约阿森西奥至2023 年薪350万欧解约金5亿","zoneImage":"www.baidu.com/3.jpg","publishTime":1506666958000,"yn":0,"updateTime":null},{"id":4,"userId":4,"zoneContent":"英国发现罗马时期铜制小狗雕像 伸舌头表情生动","zoneImage":"www.baidu.com/4.jpg","publishTime":1506666997000,"yn":0,"updateTime":null},{"id":5,"userId":5,"zoneContent":"中国全面\u201c封杀\u201d比特币 外媒：戳破国际市场泡沫","zoneImage":"www.baidu.com/5.jpg","publishTime":1506667025000,"yn":0,"updateTime":null},{"id":6,"userId":2,"zoneContent":"测试内容","zoneImage":"www.baidu.com/dd.jpg","publishTime":1506674926000,"yn":1,"updateTime":1506676310000},{"id":7,"userId":2,"zoneContent":"测试内容1","zoneImage":"www.baidu.com/dd1.jpg","publishTime":1506675017000,"yn":0,"updateTime":null},{"id":8,"userId":3,"zoneContent":"测试内容3","zoneImage":"www.baidu.com/dd3.jpg","publishTime":1506676061000,"yn":0,"updateTime":null},{"id":9,"userId":1,"zoneContent":"我的动态","zoneImage":"","publishTime":1509958977000,"yn":0,"updateTime":null},{"id":10,"userId":3,"zoneContent":"测试","zoneImage":"","publishTime":1513601214000,"yn":0,"updateTime":null}]
     */

    private int pageNo;
    private int pageSize;
    private int maxPageSize;
    private int totalCount;
    private List<ResultListBean> resultList;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getMaxPageSize() {
        return maxPageSize;
    }

    public void setMaxPageSize(int maxPageSize) {
        this.maxPageSize = maxPageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<ResultListBean> getResultList() {
        return resultList;
    }

    public void setResultList(List<ResultListBean> resultList) {
        this.resultList = resultList;
    }

    public static class ResultListBean {
        /**
         * id : 1
         * userId : 1
         * zoneContent : 一个十年门户老编的哀叹：被今日头条革了命
         * zoneImage : www.baidu.com/1.jpg
         * publishTime : 1506666888000
         * yn : 0
         * updateTime : null
         */

        private int id;
        private int userId;
        private String zoneContent;
        private String zoneImage;
        private long publishTime;
        private int yn;
        private long updateTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getZoneContent() {
            return zoneContent;
        }

        public void setZoneContent(String zoneContent) {
            this.zoneContent = zoneContent;
        }

        public String getZoneImage() {
            return zoneImage;
        }

        public void setZoneImage(String zoneImage) {
            this.zoneImage = zoneImage;
        }

        public long getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(long publishTime) {
            this.publishTime = publishTime;
        }

        public int getYn() {
            return yn;
        }

        public void setYn(int yn) {
            this.yn = yn;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }
    }
}
