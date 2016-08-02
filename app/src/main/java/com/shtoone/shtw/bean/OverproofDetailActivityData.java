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
     * gongdanhao : 17
     * jianliresult :
     * chulijieguo :
     * jiaobanshijian : 50
     * wentiyuanyin :
     * chuliaoshijian : 2016-03-11 19:56:54
     * peifanghao : TJ2-B-HNT-21
     * chulishijian :
     * qiangdudengji : C30防水砼
     * shenhe :
     * gongchengmingcheng : 云南省蒙自至文山至砚山高速公路第2合同段
     * chaozuozhe : 马耀先
     * gujifangshu : 0.980141667922338
     * waijiajipingzhong : 1
     * sigongdidian : 老鹰山隧道进口 隧道二队
     * banhezhanminchen : 一航局二分部2号站1号机
     * shuinipingzhong : 160311195101
     * shenpidate :
     * jiaozuobuwei : 老进右洞K15+425-437二衬
     */

    private HeadMsgBean headMsg;
    /**
     * headMsg : {"jianlishenpi":"","confirmdate":"","chuliren":"","chuli":"","shenpiren":"","id":"","chulifangshi":"","gongdanhao":"17","jianliresult":"","chulijieguo":"","jiaobanshijian":"50","wentiyuanyin":"","chuliaoshijian":"2016-03-11 19:56:54","peifanghao":"TJ2-B-HNT-21","chulishijian":"","qiangdudengji":"C30防水砼","shenhe":"","gongchengmingcheng":"云南省蒙自至文山至砚山高速公路第2合同段","chaozuozhe":"马耀先","gujifangshu":"0.980141667922338","waijiajipingzhong":"1","sigongdidian":"老鹰山隧道进口 隧道二队","banhezhanminchen":"一航局二分部2号站1号机","shuinipingzhong":"160311195101","shenpidate":"","jiaozuobuwei":"老进右洞K15+425-437二衬"}
     * data : [{"peibi":"870.00","name":"砂1","shiji":"843.00","wuchazhi":"-27.00"},{"peibi":"588.00","name":"碎石","shiji":"571.00","wuchazhi":"-17.00"},{"peibi":"294.00","name":"碎石1","shiji":"294.00","wuchazhi":"0.00"},{"peibi":"98.00","name":"碎石2","shiji":"99.00","wuchazhi":"1.00"},{"peibi":"302.00","name":"水泥1","shiji":"301.10","wuchazhi":"-0.90"},{"peibi":"0.00","name":"水泥2","shiji":"0.00","wuchazhi":"0.00"},{"peibi":"0.00","name":"矿粉","shiji":"0.00","wuchazhi":"0.00"},{"peibi":"0.00","name":"煤灰","shiji":"0.00","wuchazhi":"0.00"},{"peibi":"168.00","name":"水","shiji":"166.50","wuchazhi":"-1.50"},{"peibi":"4.15","name":"减水剂2","shiji":"4.04","wuchazhi":"-0.11"}]
     * success : true
     */

    private boolean success;
    /**
     * peibi : 870.00
     * name : 砂1
     * shiji : 843.00
     * wuchazhi : -27.00
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
        private String chulishijian;
        private String qiangdudengji;
        private String shenhe;
        private String gongchengmingcheng;
        private String chaozuozhe;
        private String gujifangshu;
        private String waijiajipingzhong;
        private String sigongdidian;
        private String banhezhanminchen;
        private String shuinipingzhong;
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

        public String getBanhezhanminchen() {
            return banhezhanminchen;
        }

        public void setBanhezhanminchen(String banhezhanminchen) {
            this.banhezhanminchen = banhezhanminchen;
        }

        public String getShuinipingzhong() {
            return shuinipingzhong;
        }

        public void setShuinipingzhong(String shuinipingzhong) {
            this.shuinipingzhong = shuinipingzhong;
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
