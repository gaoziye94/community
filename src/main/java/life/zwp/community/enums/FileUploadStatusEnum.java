package life.zwp.community.enums;

import lombok.Data;

public enum FileUploadStatusEnum {
    SUCCESS(0,"成功"),
    FAIL(1,"失败");

    private Integer status;
    private String statusName;

    FileUploadStatusEnum() {}

    FileUploadStatusEnum(Integer status, String statusName) {
        this.status = status;
        this.statusName = statusName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
