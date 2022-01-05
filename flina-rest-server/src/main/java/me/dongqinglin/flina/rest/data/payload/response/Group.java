package me.dongqinglin.flina.rest.data.payload.response;

import me.dongqinglin.flina.rest.data.schema.entity.Series;
import me.dongqinglin.flina.rest.data.schema.entity.Status;
import me.dongqinglin.flina.rest.data.schema.entity.Visitor;
import me.dongqinglin.flina.rest.data.schema.entity.WorksToStyleWithStatus;

import java.util.ArrayList;
import java.util.List;

public class Group {

    private Series series;

    private Status status;

    private List<WorksToStyleWithStatus> worksList = new ArrayList<>();

    private List<Visitor> visitorList = new ArrayList<>();

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<WorksToStyleWithStatus> getWorksList() {
        return worksList;
    }

    public void setWorksList(List<WorksToStyleWithStatus> worksList) {
        this.worksList = worksList;
    }

    public List<Visitor> getVisitorList() {
        return visitorList;
    }

    public void setVisitorList(List<Visitor> visitorList) {
        this.visitorList = visitorList;
    }
}
