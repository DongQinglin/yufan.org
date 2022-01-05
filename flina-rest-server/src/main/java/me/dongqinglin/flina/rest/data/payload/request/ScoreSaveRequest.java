package me.dongqinglin.flina.rest.data.payload.request;

public class ScoreSaveRequest {
    private Long scoreId;
    private Long worksId;
    private Long score;
    private Long editorId;
    private String editorName;
    private String editedReason;

    public Long getScoreId() {
        return scoreId;
    }

    public void setScoreId(Long scoreId) {
        this.scoreId = scoreId;
    }

    public Long getWorksId() {
        return worksId;
    }

    public void setWorksId(Long worksId) {
        this.worksId = worksId;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public Long getEditorId() {
        return editorId;
    }

    public void setEditorId(Long editorId) {
        this.editorId = editorId;
    }

    public String getEditorName() {
        return editorName;
    }

    public void setEditorName(String editorName) {
        this.editorName = editorName;
    }

    public String getEditedReason() {
        return editedReason;
    }

    public void setEditedReason(String editedReason) {
        this.editedReason = editedReason;
    }
}
