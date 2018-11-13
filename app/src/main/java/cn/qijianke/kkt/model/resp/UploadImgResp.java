package cn.qijianke.kkt.model.resp;

import cn.qijianke.kkt.model.BaseModel;

public class UploadImgResp extends BaseModel {

    private String fileName;

    private String imgPath;

    public UploadImgResp() {
    }

    public UploadImgResp(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getImgPath() {
        return imgPath;
    }

    public UploadImgResp setImgPath(String imgPath) {
        this.imgPath = imgPath;
        return this;
    }
}
