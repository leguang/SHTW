package com.shtoone.shtw.bean;

import java.util.List;

/**
 * Created by leguang on 2016/7/25 0025.
 */
public class ProduceQueryDetailActivityData {

    /**
     * chuliaoshijian : 2016-07-15 17:10:24
     * peifanghao : C50T
     * qiangdudengji : C50
     * gongchengmingcheng : 荒寨2#大桥左幅第14跨湿接缝及横隔板
     * chaozuozhe : 熊胜 15870449469
     * id :
     * gongdanhao : 12067
     * gujifangshu : 1
     * waijiajipingzhong : 1
     * sigongdidian : 中交二航局蒙文砚高速
     * banhezhanminchen : 二航局2分部2号站3号机
     * jiaobanshijian : 120
     * shuinipingzhong :
     * jiaozuobuwei :
     */

    private HeadMsgBean headMsg;
    /**
     * headMsg : {"chuliaoshijian":"2016-07-15 17:10:24","peifanghao":"C50T","qiangdudengji":"C50","gongchengmingcheng":"荒寨2#大桥左幅第14跨湿接缝及横隔板","chaozuozhe":"熊胜 15870449469","id":"","gongdanhao":"12067","gujifangshu":"1","waijiajipingzhong":"1","sigongdidian":"中交二航局蒙文砚高速","banhezhanminchen":"二航局2分部2号站3号机","jiaobanshijian":"120","shuinipingzhong":"","jiaozuobuwei":""}
     * data : [{"peibi":"0","name":"砂1","shiji":"0","wuchazhi":"0.00"},{"peibi":"830","name":"砂2","shiji":"829","wuchazhi":"-1.00"},{"peibi":"870","name":"碎石1","shiji":"870","wuchazhi":"0.00"},{"peibi":"100","name":"碎石2","shiji":"100","wuchazhi":"0.00"},{"peibi":"0","name":"水泥1","shiji":"0","wuchazhi":"0.00"},{"peibi":"480","name":"水泥2","shiji":"479","wuchazhi":"-1.00"},{"peibi":"0","name":"矿粉","shiji":"0","wuchazhi":"0.00"},{"peibi":"0","name":"粉煤灰","shiji":"0","wuchazhi":"0.00"},{"peibi":"178","name":"水","shiji":"178.800003051758","wuchazhi":"0.80"},{"peibi":"4.59999990463257","name":"减水剂1","shiji":"4.59999990463257","wuchazhi":"0.00"}]
     * success : true
     */

    private boolean success;
    /**
     * peibi : 0
     * name : 砂1
     * shiji : 0
     * wuchazhi : 0.00
     */

    private List<DataBean> data;

    public HeadMsgBean getHeadMsg() {
        return headMsg;
    }

    public void setHeadMsg(HeadMsgBean headMsg) {
        this.headMsg = headMsg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class HeadMsgBean {
        private String chuliaoshijian;
        private String peifanghao;
        private String qiangdudengji;
        private String gongchengmingcheng;
        private String chaozuozhe;
        private String id;
        private String gongdanhao;
        private String gujifangshu;
        private String waijiajipingzhong;
        private String sigongdidian;
        private String banhezhanminchen;
        private String jiaobanshijian;
        private String shuinipingzhong;
        private String jiaozuobuwei;

        public String getChuliaoshijian() {
            return chuliaoshijian;
        }

        public void setChuliaoshijian(String chuliaoshijian) {
            this.chuliaoshijian = chuliaoshijian;
        }

        public String getPeifanghao() {
            return peifanghao;
        }

        public void setPeifanghao(String peifanghao) {
            this.peifanghao = peifanghao;
        }

        public String getQiangdudengji() {
            return qiangdudengji;
        }

        public void setQiangdudengji(String qiangdudengji) {
            this.qiangdudengji = qiangdudengji;
        }

        public String getGongchengmingcheng() {
            return gongchengmingcheng;
        }

        public void setGongchengmingcheng(String gongchengmingcheng) {
            this.gongchengmingcheng = gongchengmingcheng;
        }

        public String getChaozuozhe() {
            return chaozuozhe;
        }

        public void setChaozuozhe(String chaozuozhe) {
            this.chaozuozhe = chaozuozhe;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGongdanhao() {
            return gongdanhao;
        }

        public void setGongdanhao(String gongdanhao) {
            this.gongdanhao = gongdanhao;
        }

        public String getGujifangshu() {
            return gujifangshu;
        }

        public void setGujifangshu(String gujifangshu) {
            this.gujifangshu = gujifangshu;
        }

        public String getWaijiajipingzhong() {
            return waijiajipingzhong;
        }

        public void setWaijiajipingzhong(String waijiajipingzhong) {
            this.waijiajipingzhong = waijiajipingzhong;
        }

        public String getSigongdidian() {
            return sigongdidian;
        }

        public void setSigongdidian(String sigongdidian) {
            this.sigongdidian = sigongdidian;
        }

        public String getBanhezhanminchen() {
            return banhezhanminchen;
        }

        public void setBanhezhanminchen(String banhezhanminchen) {
            this.banhezhanminchen = banhezhanminchen;
        }

        public String getJiaobanshijian() {
            return jiaobanshijian;
        }

        public void setJiaobanshijian(String jiaobanshijian) {
            this.jiaobanshijian = jiaobanshijian;
        }

        public String getShuinipingzhong() {
            return shuinipingzhong;
        }

        public void setShuinipingzhong(String shuinipingzhong) {
            this.shuinipingzhong = shuinipingzhong;
        }

        public String getJiaozuobuwei() {
            return jiaozuobuwei;
        }

        public void setJiaozuobuwei(String jiaozuobuwei) {
            this.jiaozuobuwei = jiaozuobuwei;
        }
    }

    public static class DataBean {
        private String peibi;
        private String name;
        private String shiji;
        private String wuchazhi;

        public String getPeibi() {
            return peibi;
        }

        public void setPeibi(String peibi) {
            this.peibi = peibi;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getShiji() {
            return shiji;
        }

        public void setShiji(String shiji) {
            this.shiji = shiji;
        }

        public String getWuchazhi() {
            return wuchazhi;
        }

        public void setWuchazhi(String wuchazhi) {
            this.wuchazhi = wuchazhi;
        }
    }
}
