package com.minyou.manba.network.resultModel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.minyou.manba.BR;

/**
 * Created by luchunhao on 2018/1/11.
 */

public class GuildDetailResultModel extends BaseResultModel {


    /**
     * detail : null
     * result : {"guildId":3,"guildName":"情感&日常","memberNum":20,"liveness":null,"guildPhoto":"http://res.mymanba.cn/FiBsC4T-7xRFyiB_6owprDKnWnUr","declaration":"测试","createUser":1,"currentNum":0}
     */

    private GuildDetailBean result;

    public GuildDetailBean getResult() {
        return result;
    }

    public void setResult(GuildDetailBean result) {
        this.result = result;
    }

    public static class GuildDetailBean extends BaseObservable {
        /**
         * guildId : 3
         * guildName : 情感&日常
         * memberNum : 20
         * liveness : null
         * guildPhoto : http://res.mymanba.cn/FiBsC4T-7xRFyiB_6owprDKnWnUr
         * declaration : 测试
         * createUser : 1
         * currentNum : 0
         */

        private int guildId;
        private String guildName;
        private int memberNum;
        private int liveness;
        private String guildPhoto;
        private String declaration;
        private int createUser;
        private int currentNum;
        private boolean guildMember;
        private int zoneNum;

        public int getZoneNum() {
            return zoneNum;
        }

        public void setZoneNum(int zoneNum) {
            this.zoneNum = zoneNum;
        }

        public int getGuildId() {
            return guildId;
        }

        public void setGuildId(int guildId) {
            this.guildId = guildId;
        }

        public String getGuildName() {
            return guildName;
        }

        public void setGuildName(String guildName) {
            this.guildName = guildName;
        }

        public int getMemberNum() {
            return memberNum;
        }

        public void setMemberNum(int memberNum) {
            this.memberNum = memberNum;
        }

        public int getLiveness() {
            return liveness;
        }

        public void setLiveness(int liveness) {
            this.liveness = liveness;
        }

        public String getGuildPhoto() {
            return guildPhoto;
        }

        public void setGuildPhoto(String guildPhoto) {
            this.guildPhoto = guildPhoto;
        }

        public String getDeclaration() {
            return declaration;
        }

        public void setDeclaration(String declaration) {
            this.declaration = declaration;
        }

        public int getCreateUser() {
            return createUser;
        }

        public void setCreateUser(int createUser) {
            this.createUser = createUser;
        }

        public int getCurrentNum() {
            return currentNum;
        }

        public void setCurrentNum(int currentNum) {
            this.currentNum = currentNum;
        }

        @Bindable
        public boolean isGuildMember() {
            return guildMember;
        }

        public void setGuildMember(boolean guildMember) {
            this.guildMember = guildMember;
            notifyPropertyChanged(BR.guildMember);
        }
    }
}
