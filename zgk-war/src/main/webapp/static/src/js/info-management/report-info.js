/**
 * Created by machengcheng on 16/11/23.
 */

$(function () {

    var url = location.href,
        uid = parseInt(url.substr(url.lastIndexOf('=') + 1, url.length));
    if (uid) {
        var reportInfo = new ReportInfo(uid);
    }

});

function ReportInfo (uid) {
    this.uid = uid;
    this.subjects = [];
    this.values = [];
    this.init();
}
ReportInfo.prototype = {
    constructor: ReportInfo,
    init: function () {
        this.queryEvaluation();
        this.getReportResult();
    },
    queryEvaluation: function () {
        var that = this;
        var subjectMaping = {
            dl: '地理',
            hx: '化学',
            ls: '历史',
            sw: '生物',
            wl: '物理',
            zz: '政治',
            ty: '通用技术'
        };
        Common.ajaxFun('/evaluation/queryEvaluationByUid.do', 'GET', {
            'uid': that.uid
        }, function (res) {
            if (res.rtnCode == "0000000") {
                var data = res.bizData;
                for (var i in data) {
                    if (i != 'cdate' && i != 'userId' && i != 'id') {
                        that.subjects.push(subjectMaping[i]);
                        that.values.push(data[i]);
                    }
                }
                console.info(that.subjects);
                console.info(that.values);
            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
    },
    getReportResult: function () {
        var that = this;
        var reportChart = echarts.init(document.getElementById('reportChart'));
        var reportChartOption = {
            title: {
                show: true,
                text: '测评报告',
                x: 'center',
                top: 'top',
                textStyle: {
                    color: '#4A4A4A',
                    fontWeight: 'normal',
                    fontSize: 14
                }
            },
            tooltip: {
                trigger: 'axis'
            },
            toolbox: {
                show: false
            },
            calculable: false,
            xAxis: [
                {
                    type: 'category',
                    name: '课程名称',
                    nameTextStyle: {
                        color: '#4A4A4A',
                        fontSize: 14
                    },
                    data: that.subjects,
                    axisLine: {
                        lineStyle: {
                            color: '#D8D8D8'
                        }
                    },
                    splitLine: {
                        show: false
                    },
                    axisTick: {
                        show: false
                    }
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    name: '分数',
                    nameTextStyle: {
                        color: '#4A4A4A',
                        fontSize: 14
                    },
                    //min: Math.min(stuNumbers),
                    //max: Math.max(stuNumbers),
                    axisLine: {
                        lineStyle: {
                            color: '#D8D8D8'
                        }
                    },
                    splitLine: {
                        show: true
                    },
                    axisLabel: {
                        formatter: '{value}'
                    },
                    axisTick: {
                        show: false
                    }
                }
            ],
            series: [
                {
                    name: '分数',
                    type: 'bar',
                    barWidth: 44,
                    data: that.values,
                    label: {
                        normal: {
                            show: true,
                            position: 'top',
                            formatter: '{c}'
                        }
                    },
                    itemStyle: {
                        normal: {
                            color: '#108EE9'
                        }
                    }
                }
            ]
        };
        reportChart.setOption(reportChartOption);
    }
};