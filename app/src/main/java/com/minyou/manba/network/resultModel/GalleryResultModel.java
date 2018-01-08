package com.minyou.manba.network.resultModel;

import java.util.List;

/**
 * Created by Administrator on 2018/1/8.
 */

public class GalleryResultModel extends BaseResultModel {

    /**
     * detail : null
     * result : {"pageNo":1,"pageSize":10,"maxPageSize":100,"totalCount":18,"resultList":["http://res.mymanba.cn/FqzxBoScyQw8OUnTqbWMzZD_uIUk","http://res.mymanba.cn/Fmr7r1GbYH8vNa_mMlYxJIyE5x2W","http://res.mymanba.cn/FjGGWyKH-QbszD5B7garS7_7QNPt","http://res.mymanba.cn/FvEzf7BDw81KQqtlHjw-4sFJ6WRh","http://res.mymanba.cn/Fr4Irc5fvlHO8x61dWN6tkH4wIIc","http://res.mymanba.cn/FjpdgaQ1gRJqHeEQPY-cL1-syVqK","http://res.mymanba.cn/Fh_LcC5wZOtRoKSuPIOTwyEhSXbR","http://res.mymanba.cn/Fh_LcC5wZOtRoKSuPIOTwyEhSXbR","http://res.mymanba.cn/FpyGEpqaAkiiUs-hAMgVPFju60Ih","http://res.mymanba.cn/FjXdjCUuj2cO658zlnznZQMvr-0f"]}
     */

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * pageNo : 1
         * pageSize : 10
         * maxPageSize : 100
         * totalCount : 18
         * resultList : ["http://res.mymanba.cn/FqzxBoScyQw8OUnTqbWMzZD_uIUk","http://res.mymanba.cn/Fmr7r1GbYH8vNa_mMlYxJIyE5x2W","http://res.mymanba.cn/FjGGWyKH-QbszD5B7garS7_7QNPt","http://res.mymanba.cn/FvEzf7BDw81KQqtlHjw-4sFJ6WRh","http://res.mymanba.cn/Fr4Irc5fvlHO8x61dWN6tkH4wIIc","http://res.mymanba.cn/FjpdgaQ1gRJqHeEQPY-cL1-syVqK","http://res.mymanba.cn/Fh_LcC5wZOtRoKSuPIOTwyEhSXbR","http://res.mymanba.cn/Fh_LcC5wZOtRoKSuPIOTwyEhSXbR","http://res.mymanba.cn/FpyGEpqaAkiiUs-hAMgVPFju60Ih","http://res.mymanba.cn/FjXdjCUuj2cO658zlnznZQMvr-0f"]
         */

        private int pageNo;
        private int pageSize;
        private int maxPageSize;
        private int totalCount;
        private List<String> resultList;

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

        public List<String> getResultList() {
            return resultList;
        }

        public void setResultList(List<String> resultList) {
            this.resultList = resultList;
        }
    }
}
