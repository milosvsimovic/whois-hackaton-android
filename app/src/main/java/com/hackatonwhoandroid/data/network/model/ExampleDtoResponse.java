package com.hackatonwhoandroid.data.network.model;

import org.mapstruct.Mapper;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExampleDtoResponse {

//    @SerializedName("id")
//    @Expose
//    public Long id;
//    @SerializedName("name")
//    @Expose
//    public String name;
//    @SerializedName("start")
//    @Expose
//    public String start;
//    @SerializedName("hour")
//    @Expose
//    public String hour;
//    @SerializedName("workdays")
//    @Expose
//    public String workdays;
//    @SerializedName("saturday")
//    @Expose
//    public String saturday;
//    @SerializedName("sunday")
//    @Expose
//    public String sunday;

    @Mapper
    public interface Mappers {
//        Departure map(ExampleResponse response);
//        Departure map(DepartureEntity entity);
//
//        @IterableMapping(elementTargetType = ExampleResponse.class)
//        List<Departure> mapAll(List<ExampleResponse> list);
//
//        List<Departure> mapAllFromEntity(List<DepartureEntity> list);
    }
}
