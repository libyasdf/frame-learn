var chart_0,chart_1,chart_2,chart_3,chart_4,chart_5;
var colors = ['#5793f3', '#d14a61', '#675bba'];

// 任务状态分布
var  opt_chart_0 = {
    title:{
        text:'任务状态分布',
    },
    grid: {
    },
    tooltip: {
        trigger: 'item',
        formatter: "{a} <br/>{b}: {c} ({d}%)"
    },
    legend: {
        // orient: 'vertical',
        x: 'right',
        y:20,
        data:['按期办结','逾期办结','任务取消','正在办理']
    },
    series: [
        {
            name:'任务状态分布',
            type:'pie',
            center:['50%','60%'],
            radius: ['40%', '70%'],
            avoidLabelOverlap: false,
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            },
            data:[
                {value:50, name:'按期办结'},
                {value:20, name:'逾期办结'},
                {value:10, name:'任务取消'},
                {value:12, name:'正在办理'}
            ]
        }
    ]
}

// 承办单位任务统计
var opt_chart_1 = {
    color: colors,
    title:{
        text:'承办单位任务统计',
    },
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'cross'
        }
    },
    grid: {
        top:'20%',
        right: '10%',
    },
    toolbox: {
    },
    legend: {
        // orient: 'vertical',
        data:['任务数量','平均反馈退回次数','平均延期申请次数','平均解锁申请次数'],
        x:'right',
        y:20
    },
    xAxis: [
        {
            type: 'category',
            axisTick: {
                alignWithLabel: true
            },
            data: ['财务处','采购处','秘书处','公文办理处','评审处','综合处','办公二处','办公一处','前端处','后端处','信息部门','内勤部']
        }
    ],
    yAxis: [
        {
            type: 'value',
            name: '次数',
            position: 'right',
            offset: 0,
            axisLine: {
                lineStyle: {
                    // color: colors[1]
                }
            },
            axisLabel: {
                formatter: '{value} 次'
            },
            splitLine:{
                lineStyle:{
                    // color: colors[1]
                }
            }
        },
        {
            type: 'value',
            name: '数量',
            position: 'left',
            axisLine: {
                lineStyle: {
                    // color: colors[2]
                }
            },
            axisLabel: {
                formatter: '{value} 个'
            },
            splitLine:{
                lineStyle:{
                    // color: colors[2]
                }
            }
        }
    ],
    series: [
        {
            name:'任务数量',
            type:'bar',
            yAxisIndex: 1,
            data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3]
        },
        {
            name:'平均反馈退回次数',
            type:'line',
            yAxisIndex: 0,
            data:[100.0, 50.9, 32.0, 53.2, 85.6, 77.7, 65.6, 162.2, 15.6, 110.0, 95.4, 100.3]
        },
        {
            name:'平均延期申请次数',
            type:'line',
            yAxisIndex: 0,
            data:[85.0, 72.2, 95.3, 180.5, 92.3, 77.2, 65.3, 55.4, 43.0, 32.5, 12.0, 6.2]
        },
        {
            name:'平均解锁申请次数',
            type:'line',
            yAxisIndex: 0,
            data:[2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5, 12.0, 6.2]
        }
    ]
}

// 承办单位效率统计
var opt_chart_2 = {
    title: {
        text: '承办单位效率统计'
    },
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        data:['办结率','按期办结率','超期办结率'],
        x:'right',
        // right:'10%',
        y:20
    },
    grid: {
        left: '3%',
        right: '5%',
        bottom: '3%',
        containLabel: true
    },
    xAxis: {
        type: 'category',
        boundaryGap: false,
        data: ['财务处','采购处','秘书处','公文办理处','评审处','综合处','办公二处']
    },
    yAxis: {
        type: 'value'
    },
    series: [
        {
            name:'办结率',
            type:'line',
            stack: '总量',
            data:[120, 132, 101, 134, 90, 230, 210]
        },
        {
            name:'按期办结率',
            type:'line',
            stack: '总量',
            data:[220, 182, 191, 234, 290, 330, 310]
        },
        {
            name:'超期办结率',
            type:'line',
            stack: '总量',
            data:[150, 232, 201, 154, 190, 330, 410]
        }
    ]
}

// 任务年度统计
var opt_chart_3 = {
    color: colors,
    title: {
        text: '2019年度任务统计'
    },
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'cross'
        }
    },
    grid: {
        top:'20%',
        left: '10%',
        right: '3%',
        bottom: '6%',
    },
    legend: {
        data:['发布任务数','办结任务数'],
        x:'right',
        y:20
    },
    xAxis: [
        {
            type: 'category',
            axisTick: {
                alignWithLabel: true
            },
            data: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
        }
    ],
    yAxis: [
        {
            type: 'value',
            name: '任务数',
            position: 'left',
            axisLine: {
                lineStyle: {
                    color: colors[2]
                }
            },
            axisLabel: {
                formatter: '{value} 个'
            }
        }
    ],
    series: [
        {
            name:'发布任务数',
            type:'bar',
            yAxisIndex: 0,
            data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3]
        },
        {
            name:'办结任务数',
            type:'line',
            yAxisIndex: 0,
            data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3]
        }
    ]
}

// 办结时长分布
var opt_chart_4 = {
    title: {
        text: '办结时长分布'
    },
    tooltip: {
        trigger: 'item',
        formatter: "{a} <br/>{b}: {c} ({d}%)"
    },
    legend: {
        // orient: 'vertical',
        x: 'right',
        align:'left',
        data:['0≈3个月','3≈6个月','6-12个月','>1年']
    },
    series: [
        {
            name:'办结时长分布',
            type:'pie',
            selectedMode: 'single',
            radius: [0, '30%'],
            center:['50%','50%'],
            label: {
                normal: {
                    position: 'inner',
                    show:false
                }
            },
            labelLine: {
                normal: {
                    show: false
                }
            },
            data:[
                {value:335, name:'0≈3个月', selected:true},
                {value:679, name:'3≈6个月'},
                {value:1548, name:'6-12个月'},
                {value:1548, name:'>1年'}
            ]
        },
        {
            name:'办结时长分布',
            type:'pie',
            radius: ['40%', '55%'],
            center:['50%','50%'],
            label: {
                normal: {
                    formatter: '{a|{a}}{abg|}\n{hr|}\n  {b|{b}：}{c}  {per|{d}%}  ',
                    backgroundColor: '#eee',
                    borderColor: '#aaa',
                    borderWidth: 1,
                    borderRadius: 4,
                    // shadowBlur:3,
                    // shadowOffsetX: 2,
                    // shadowOffsetY: 2,
                    // shadowColor: '#999',
                    // padding: [0, 7],
                    rich: {
                        a: {
                            color: '#999',
                            lineHeight: 22,
                            align: 'center'
                        },
                        // abg: {
                        //     backgroundColor: '#333',
                        //     width: '100%',
                        //     align: 'right',
                        //     height: 22,
                        //     borderRadius: [4, 4, 0, 0]
                        // },
                        hr: {
                            borderColor: '#aaa',
                            width: '100%',
                            borderWidth: 0.5,
                            height: 0
                        },
                        b: {
                            fontSize: 16,
                            lineHeight: 33
                        },
                        per: {
                            color: '#eee',
                            backgroundColor: '#334455',
                            padding: [2, 4],
                            borderRadius: 2
                        }
                    }
                }
            },
            data:[
                {value:335, name:'按期办结'},
                {value:310, name:'超期办结'},
                {value:234, name:'按期办结'},
                {value:135, name:'超期办结'},
                {value:1048, name:'按期办结'},
                {value:251, name:'超期办结'},
                {value:147, name:'按期办结'},
                {value:102, name:'超期办结'}
            ]
        }
    ]
}

// 任务要求时长分布
var opt_chart_5 = {
    title : {
        text: '任务要求时长分布',
        // subtext: '纯属虚构',
        x:'left'
    },
    tooltip : {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
        // orient: 'vertical',
        x: 'right',
        data: ['0≈3个月','3≈6个月','6≈12个月','>1年']
    },
    series : [
        {
            name: '完成时限',
            type: 'pie',
            radius : '55%',
            center: ['50%', '60%'],
            data:[
                {value:335, name:'0≈3个月'},
                {value:310, name:'3≈6个月'},
                {value:234, name:'6≈12个月'},
                {value:135, name:'>1年'}
            ],
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
}


$(document).ready(function () {
    // 基于准备好的dom，初始化echarts实例
    setTimeout(function () {
        chart_0 = echarts.init(document.getElementById('container0')).setOption(opt_chart_0);
        chart_1 = echarts.init(document.getElementById('container1')).setOption(opt_chart_1);
        chart_2 = echarts.init(document.getElementById('container2')).setOption(opt_chart_2);
        chart_3 = echarts.init(document.getElementById('container3')).setOption(opt_chart_3);
        chart_4 = echarts.init(document.getElementById('container4')).setOption(opt_chart_4);
        chart_5 = echarts.init(document.getElementById('container5')).setOption(opt_chart_5);
    },0)
})





