package com.oneplatform.system.dao.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jeesuite.mybatis.core.BaseEntity;

@Table(name = "sys_api_statistics")
public class ApiStatisticsEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "api_name")
    private String apiName;

    @Column(name = "api_uri")
    private String apiUri;

    @Column(name = "call_nums")
    private Integer callNums;

    @Column(name = "fail_nums")
    private Integer failNums;

    @Column(name = "stat_date")
    private Date statDate;

    @Column(name = "min_use_time")
    private Integer minUseTime;

    @Column(name = "max_use_time")
    private Integer maxUseTime;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return api_name
     */
    public String getApiName() {
        return apiName;
    }

    /**
     * @param apiName
     */
    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    /**
     * @return api_uri
     */
    public String getApiUri() {
        return apiUri;
    }

    /**
     * @param apiUri
     */
    public void setApiUri(String apiUri) {
        this.apiUri = apiUri;
    }

    /**
     * @return call_nums
     */
    public Integer getCallNums() {
        return callNums;
    }

    /**
     * @param callNums
     */
    public void setCallNums(Integer callNums) {
        this.callNums = callNums;
    }

    /**
     * @return fail_nums
     */
    public Integer getFailNums() {
        return failNums;
    }

    /**
     * @param failNums
     */
    public void setFailNums(Integer failNums) {
        this.failNums = failNums;
    }

    /**
     * @return stat_date
     */
    public Date getStatDate() {
        return statDate;
    }

    /**
     * @param statDate
     */
    public void setStatDate(Date statDate) {
        this.statDate = statDate;
    }

    /**
     * @return min_use_time
     */
    public Integer getMinUseTime() {
        return minUseTime;
    }

    /**
     * @param minUseTime
     */
    public void setMinUseTime(Integer minUseTime) {
        this.minUseTime = minUseTime;
    }

    /**
     * @return max_use_time
     */
    public Integer getMaxUseTime() {
        return maxUseTime;
    }

    /**
     * @param maxUseTime
     */
    public void setMaxUseTime(Integer maxUseTime) {
        this.maxUseTime = maxUseTime;
    }
}