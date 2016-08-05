package com.shtoone.shtw.bean;

import java.util.List;

/**
 * Created by leguang on 2016/7/29 0029.
 */
public class OverproofDetailActivityData {

    /**
     * jianlishenpi :
     * confirmdate :
     * chuliren :
     * chuli :
     * shenpiren :
     * id :
     * chulifangshi :
     * gongdanhao : 20150320-001
     * jianliresult :
     * chulijieguo :
     * jiaobanshijian : 50
     * wentiyuanyin :
     * chuliaoshijian : 2016-07-15 17:02:37
     * peifanghao : 1
     * xinxibianhao :
     * chulishijian :
     * qiangdudengji : C20
     * shenhe :
     * gongchengmingcheng : 蒙文砚高速公路项目经理部四分部
     * chaozuozhe : 邢云华
     * gujifangshu : 1.422
     * waijiajipingzhong : 1.42
     * sigongdidian : 项目部
     * shuinipingzhong : 14287|5
     * banhezhanminchen : 三公局三公司3分部2号站1号机
     * shenpidate :
     * jiaozuobuwei : 基础
     */

    private HeadMsgBean headMsg;
    /**
     * headMsg : {"jianlishenpi":"","confirmdate":"","chuliren":"","chuli":"","shenpiren":"","id":"","chulifangshi":"","gongdanhao":"20150320-001","jianliresult":"","chulijieguo":"","jiaobanshijian":"50","wentiyuanyin":"","chuliaoshijian":"2016-07-15 17:02:37","peifanghao":"1","xinxibianhao":"","chulishijian":"","qiangdudengji":"C20","shenhe":"","gongchengmingcheng":"蒙文砚高速公路项目经理部四分部","chaozuozhe":"邢云华","gujifangshu":"1.422","waijiajipingzhong":"1.42","sigongdidian":"项目部","shuinipingzhong":"14287|5","banhezhanminchen":"三公局三公司3分部2号站1号机","shenpidate":"","jiaozuobuwei":"基础"}
     * data : [{"peibi":"313.00","name":"砂1","shiji":"315.00","wuchazhi":"2.00","wuchalv":"0.63"},{"peibi":"1006.00","name":"砂2","shiji":"1020.00","wuchazhi":"14.00","wuchalv":"1.37"},{"peibi":"470.00","name":"碎石1","shiji":"451.00","wuchazhi":"-19.00","wuchalv":"-4.21"},{"peibi":"708.00","name":"碎石2","shiji":"712.00","wuchazhi":"4.00","wuchalv":"0.56"},{"peibi":"0.00","name":"水泥1","shiji":"0.00","wuchazhi":"0.00","wuchalv":"0.00"},{"peibi":"283.00","name":"水泥2","shiji":"289.50","wuchazhi":"6.50","wuchalv":"2.25"},{"peibi":"0.00","name":"矿粉","shiji":"0.00","wuchazhi":"0.00","wuchalv":"0.00"},{"peibi":"0.00","name":"粉煤灰","shiji":"0.00","wuchazhi":"0.00","wuchalv":"0.00"},{"peibi":"283.00","name":"水","shiji":"284.00","wuchazhi":"1.00","wuchalv":"0.35"},{"peibi":"0.00","name":"减水剂1","shiji":"0.00","wuchazhi":"0.00","wuchalv":"0.00"}]
     * success : true
     */

    private boolean success;
    /**
     * peibi : 313.00
     * name : 砂1
     * shiji : 315.00
     * wuchazhi : 2.00
     * wuchalv : 0.63
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
        private String jianlishenpi;
        private String confirmdate;
        private String chuliren;
        private String chuli;
        private String shenpiren;
        private String id;
        private String chulifangshi;
        private String gongdanhao;
        private String jianliresult;
        private String chulijieguo;
        private String jiaobanshijian;
        private String wentiyuanyin;
        private String chuliaoshijian;
        private String peifanghao;
        private String xinxibianhao;
        private String chulishijian;
        private String qiangdudengji;
        private String shenhe;
        private String gongchengmingcheng;
        private String chaozuozhe;
        private String gujifangshu;
        private String waijiajipingzhong;
        private String sigongdidian;
        private String shuinipingzhong;
        private String banhezhanminchen;
        private String shenpidate;
        private String jiaozuobuwei;

        public String getJianlishenpi() {
            return jianlishenpi;
        }

        public void setJianlishenpi(String jianlishenpi) {
            this.jianlishenpi = jianlishenpi;
        }

        public String getConfirmdate() {
            return confirmdate;
        }

        public void setConfirmdate(String confirmdate) {
            this.confirmdate = confirmdate;
        }

        public String getChuliren() {
            return chuliren;
        }

        public void setChuliren(String chuliren) {
            this.chuliren = chuliren;
        }

        public String getChuli() {
            return chuli;
        }

        public void setChuli(String chuli) {
            this.chuli = chuli;
        }

        public String getShenpiren() {
            return shenpiren;
        }

        public void setShenpiren(String shenpiren) {
            this.shenpiren = shenpiren;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getChulifangshi() {
            return chulifangshi;
        }

        public void setChulifangshi(String chulifangshi) {
            this.chulifangshi = chulifangshi;
        }

        public String getGongdanhao() {
            return gongdanhao;
        }

        public void setGongdanhao(String gongdanhao) {
            this.gongdanhao = gongdanhao;
        }

        public String getJianliresult() {
            return jianliresult;
        }

        public void setJianliresult(String jianliresult) {
            this.jianliresult = jianliresult;
        }

        public String getChulijieguo() {
            return chulijieguo;
        }

        public void setChulijieguo(String chulijieguo) {
            this.chulijieguo = chulijieguo;
        }

        public String getJiaobanshijian() {
            return jiaobanshijian;
        }

        public void setJiaobanshijian(String jiaobanshijian) {
            this.jiaobanshijian = jiaobanshijian;
        }

        public String getWentiyuanyin() {
            return wentiyuanyin;
        }

        public void setWentiyuanyin(String wentiyuanyin) {
            this.wentiyuanyin = wentiyuanyin;
        }

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

        public String getXinxibianhao() {
            return xinxibianhao;
        }

        public void setXinxibianhao(String xinxibianhao) {
            this.xinxibianhao = xinxibianhao;
        }

        public String getChulishijian() {
            return chulishijian;
        }

        public void setChulishijian(String chulishijian) {
            this.chulishijian = chulishijian;
        }

        public String getQiangdudengji() {
            return qiangdudengji;
        }

        public void setQiangdudengji(String qiangdudengji) {
            this.qiangdudengji = qiangdudengji;
        }

        public String getShenhe() {
            return shenhe;
        }

        public void setShenhe(String shenhe) {
            this.shenhe = shenhe;
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

        public String getShuinipingzhong() {
            return shuinipingzhong;
        }

        public void setShuinipingzhong(String shuinipingzhong) {
            this.shuinipingzhong = shuinipingzhong;
        }

        public String getBanhezhanminchen() {
            return banhezhanminchen;
        }

        public void setBanhezhanminchen(String banhezhanminchen) {
            this.banhezhanminchen = banhezhanminchen;
        }

        public String getShenpidate() {
            return shenpidate;
        }

        public void setShenpidate(String shenpidate) {
            this.shenpidate = shenpidate;
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
        private String wuchalv;

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

        public String getWuchalv() {
            return wuchalv;
        }

        public void setWuchalv(String wuchalv) {
            this.wuchalv = wuchalv;
        }
    }
}
