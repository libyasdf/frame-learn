// 逾期预警
var option_chart_1 = {
    xAxis: {
        type: 'category',
        data: ['3天内', '7天内', '15天内', '30天内', '30天以上']
    },
    yAxis: {
        name: '任务数(个)',
        type: 'value',
        // interval:50,
        axisLabel: {
            formatter: '{value} '
        }
    },
    series: [{
        data: [25, 15, 5, 36, 48],
        type: 'bar',
        barMaxWidth:80,
        itemStyle: {
            normal: {
                color: new echarts.graphic.LinearGradient(0, 1, 0, 0, [{
                    offset: 0,
                    color: "#ff9834" // 0% 处的颜色
                }, {
                    offset: 0.6,
                    color: "#ff8349" // 60% 处的颜色
                }, {
                    offset: 1,
                    color: "#ff6765" // 100% 处的颜色
                }], false)
            }
        },
        markPoint : {
            symbol:'pin',//标记类型
            symbolSize: 40,//图形大小
            itemStyle: {
                normal: {
                    borderColor: '#fff',
                    borderWidth: 1,            // 标注边线线宽，单位px，默认为1
                    label: {
                        show: true,
                        textBorderWidth:0
                    }
                }
            },
            data : [//配置项
                {value:'25',xAxis: 0, yAxis: 25},
                {value:'15',xAxis:1, yAxis: 15},
                {value:'5',xAxis:2, yAxis: 5},
                {value:'36',xAxis:3, yAxis: 36},
                {value:'48',xAxis:4, yAxis: 48},
            ]
        },
    }]
};
// 年度任务统计
var option_chart_2 = {
    title: {
        text: ''
    },
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'cross'
        }
    },
    grid: {
        top:'20%',
        left: '8%',
        right: '5%',
        bottom: '10%',
    },
    legend: {
        data:['办结任务数','未办结任务数'],
        align:'left',
        y:20,
        right:50,
        itemWidth:36,
        itemHeight:20,
        textStyle:{
            color:'#333',
            fontSize:'16px'
        }
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
            name: '任务数(个)',
            position: 'left',
            axisLine: {
                lineStyle: {
                    //color: colors[2]
                }
            },
            axisLabel: {
                formatter: '{value} '
            }
        }
    ],
    series: [
        {
            name: '办结任务数',
            type: 'bar',
            stack: '发布任务数',
            label: {
                normal: {
                    show: true,
                    position: 'insideRight'
                }
            },
            itemStyle:{
              color:'#66cc99'
            },
            data: [320, 302, 301, 334, 390, 330, 320,302, 301, 334,330, 320]
        },
        {
            name: '未办结任务数',
            type: 'bar',
            stack: '发布任务数',
            label: {
                normal: {
                    show: true,
                    position: 'insideRight'
                }
            },
            itemStyle:{
                color:'#dddddd'
            },
            data: [120, 132, 101, 134, 90, 230, 210, 132, 101, 134,330, 320]
        }
    ]
}

// 任务状态分布
var option_chart_3 = {
    title:{
        text:'',
    },
    grid: {
    },
    tooltip: {
        trigger: 'item',
        formatter: "{a} <br/>{b}: {c} ({d}%)"
    },
    legend: {
        // orient: 'vert ical',
        x: 'center',
        y:30,
        itemWidth:36,
        itemHeight:20,
        textStyle:{
            color:'#333',
            fontSize:'16px'
        },
        data:['按期办结','逾期办结','任务取消','正在办理']
    },
    series: [
        {
            name:'任务状态分布',
            type:'pie',
            center:['50%','50%'],
            radius: ['35%', '60%'],
            avoidLabelOverlap: false,
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            },
            data:[
                {value:155, name:'按期办结',itemStyle:{color:'#33cc66'}},
                {value:49, name:'逾期办结',itemStyle:{color:'#ff6666'}},
                {value:109, name:'任务取消',itemStyle:{color:'#ffcc33'}},
                {value:146, name:'正在办理',itemStyle:{color:'#3399ff'}}
            ],
            label: {
                normal: {
                    formatter: function(params ){
                        return params.name+':'+params.value+' \n('+params.percent+'%)'
                    },
                }
            }
        }
    ]
}
// 承办单位任务总览
var option_chart_4 = {
    title:{
        text:'',
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
        data:['办结任务数','未办结任务数','办结率','按期办结率','逾期办结率'],
        x:'center',
        y:20,
        itemGap:30,
        itemWidth:36,
        itemHeight:20,
        textStyle:{
            color:'#333',
            fontSize:'16px'
        },
    },
    xAxis: [
        {
            type: 'category',
            axisTick: {
                alignWithLabel: true
            },
            axisLabel : {//坐标轴刻度标签的相关设置。
                clickable:true,//并给图表添加单击事件  根据返回值判断点击的是哪里
                interval : 0,
                formatter : function(params,index){
                    if (index % 2 != 0) {
                        return '\n' + params;
                    }
                    else {
                        return params;
                    }
                }

            },
            data: ['财务处','采购处','秘书处','公文办理处','评审处','综合处','办公二处','办公一处','前端处','后端处','信息部门','内勤部']
        }
    ],
    yAxis: [
        {
            type: 'value',
            name: '办结率（%）',
            position: 'right',
            offset: 0,
            max:100,
            min:0,
            interval:20,
            axisLine: {
                lineStyle: {
                    // color: colors[1]
                }
            },
            axisLabel: {
                formatter: '{value}'
            },
            splitLine:{
                lineStyle:{
                    // color: colors[1]
                }
            }
        },
        {
            type: 'value',
            name: '任务数（个）',
            position: 'left',
            axisLine: {
                lineStyle: {
                    // color: colors[2]
                }
            },
            axisLabel: {
                formatter: '{value} '
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
            name:'办结任务数',
            stack: '总量',
            type:'bar',
            barMaxWidth:30,
            itemStyle:{color:'#3d95cc'},
            yAxisIndex: 1,
            data:[16, 22, 37, 23, 26, 30, 45, 36, 32, 20, 30, 33]
        },
        {
            name:'未办结任务数',
            stack: '总量',
            type:'bar',
            barMaxWidth:30,
            itemStyle:{color:'#ddd'},
            yAxisIndex: 1,
            data:[16, 22, 37, 23, 26, 30, 45, 36, 32, 20, 30, 33]
        },
        {
            name:'办结率',
            type:'line',
            smooth:true,  //这个是把线变成曲线
            yAxisIndex: 0,
            itemStyle:{
                color:'#3399ff'
            },
            symbolSize:12,
            lineStyle:{
                width:4
            },
            data:[10.15,18, 22.27, 38.45, 48.50, 62.68, 72.78, 85.90, 95.90, 71, 78.60, 55.32],
        },
        {
            name:'按期办结率',
            type:'line',
            smooth:true,  //这个是把线变成曲线
            yAxisIndex: 0,
            symbolSize:12,
            itemStyle:{
                color:'#33cc66'
            },
            lineStyle:{
                width:4
            },
            data:[5.30, 22.35, 38, 39.42, 45.48, 50.55, 58, 60.65, 68, 70.80, 85.90, 98 ]
        },
        {
            name:'逾期办结率',
            type:'line',
            smooth:true,  //这个是把线变成曲线
            yAxisIndex: 0,
            symbolSize:12,
            itemStyle:{
                color:'#ff6666'
            },
            lineStyle:{
                width:4
            },
            data:[1, 10.15, 25.30, 35, 40.45, 50.55, 60.62, 65, 85, 95, 99, 100]
        }
    ]
}
// 承办单位跟踪概况
var option_chart_5 = {
    color:['#ffcc66','#ff6666','#3399ff'],
    title: {
        text: ''
    },
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        data:['平均反馈退回次数','平均延期申请次数','平均申请解锁次数'],
        x:'center',
        // right:'10%',
        y:20,
        itemGap:30,
        itemWidth:36,
        itemHeight:20,
        textStyle:{
            color:'#333',
            fontSize:'16px'
        },
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
        name:'次数(次)',
        type: 'value',
        axisLabel: {
            formatter: '{value} '
        },
        // max:100,
        // min:0,
        interval:20,
    },
    series: [
        {
            name:'平均反馈退回次数',
            type:'line',
            smooth:true,  //这个是把线变成曲线
            symbolSize:12,

            lineStyle:{
                width:4
            },
            data:[50, 60, 55, 80, 90, 89, 90]
        },
        {
            name:'平均延期申请次数',
            type:'line',
            smooth:true,  //这个是把线变成曲线
            symbolSize:12,
            lineStyle:{
                width:4
            },
            data:[35, 40, 25, 60, 56, 70, 80]
        },
        {
            name:'平均申请解锁次数',
            type:'line',
            smooth:true,  //这个是把线变成曲线
            symbolSize:12,
            lineStyle:{
                width:4
            },
            data:[15, 20, 30, 20, 33, 19, 10]
        }
    ]
}
// 办结时长分布
var option_chart_6 = {
    title: {
        text: ''
    },
    tooltip: {
        trigger: 'item',
        formatter: "{a} <br/>{b}: {c} ({d}%)"
    },
    legend: {
        // orient: 'vertical',
        x: 'center',
        y:30,
        itemWidth:36,
        itemHeight:20,
        textStyle:{
            color:'#333',
            fontSize:'16px'
        },
        data:['0~3个月','3~6个月','6~12个月','>1年']
    },
    series: [
        {
            name:'办结时长分布',
            type:'pie',
            selectedMode: 'single',
            radius: [0, '30%'],
            center:['50%','60%'],
            label: {
                normal: {
                    position: 'inner',
                    show:true,
                    formatter: function(params){
                        return params.value+' ('+params.percent+'%)'
                    },
                    rich:{
                        per: {
                            padding: [2, 4],
                            borderRadius: 2
                        },
                    }

                }
            },
            labelLine: {
                normal: {
                    show: false
                }
            },
            data:[
                {
                    name:'0~3个月',
                    value:54,
                    selected:true,
                    itemStyle:{
                        color:'#33cc66'
                    }
                },
                {
                    name:'3~6个月',
                    value:50,
                    itemStyle:{
                        color:'#ff6666'
                    }
                },
                {
                    name:'6~12个月',
                    value:56,
                    itemStyle:{
                        color:'#ffcc66'
                    }
                },
                {
                    name:'>1年',
                    value:44,
                    itemStyle:{
                        color:'#3d95cc'
                    }
                }
            ]
        },
        {
            name:'',
            type:'pie',
            radius: ['40%', '55%'],
            center:['50%','60%'],
            label: {
                normal: {
                    formatter: function(params ){
                        return params.name+':'+params.value+' ('+params.percent+'%)'
                    },

                }
            },
            data:[
                { value:41, name:'按期办结', itemStyle:{color:'#5cd685'} },
                { value:13, name:'逾期办结', itemStyle:{color:'#33ad5c'} },
                { value:40, name:'按期办结', itemStyle:{color:'#ff8585'} },
                { value:10, name:'逾期办结', itemStyle:{color:'#ff5c5c'} },
                { value:40, name:'按期办结', itemStyle:{color:'#ffd65c'} },
                { value:16, name:'逾期办结', itemStyle:{color:'#d6ad33'} },
                { value:34, name:'按期办结', itemStyle:{color:'#5cadff'} },
                { value:10, name:'逾期办结', itemStyle:{color:'#3385ff'} }
            ]
            /* data:[
                   {value:41, name:'按期办结',itemStyle:{color:'#BDA29A'}},
                   {value:13, name:'逾期办结',itemStyle:{color:'#CA8622'}},
                   {value:40, name:'按期办结',itemStyle:{color:'#6E7074'}},
                   {value:10, name:'逾期办结',itemStyle:{color:'#546570'}},
                   {value:40, name:'按期办结',itemStyle:{color:'#D32824'}},
                   {value:16, name:'逾期办结',itemStyle:{color:'#D48265'}},
                   {value:34, name:'按期办结',itemStyle:{color:'#91C7AE'}},
                   {value:10, name:'逾期办结',itemStyle:{color:'#749F83'}}
               ]*/
        }
    ]
}

function initCharts() {
    $("#item-1-num").rollNum({
        deVal:99
    });
    $("#item-2-num").rollNum({
        deVal:186
    });
    $("#item-3-num").rollNum({
        deVal:257
    });
    $("#item-4-num").rollNum({
        deVal:385
    });
    $("#item-5-num").rollNum({
        deVal:7784
    });

    // 基于准备好的dom，初始化echarts实例
    var chart_1 = echarts.init(document.getElementById('chart-1'));
    var chart_2 = echarts.init(document.getElementById('chart-2'));
    var chart_3 = echarts.init(document.getElementById('chart-3'));
    var chart_4 = echarts.init(document.getElementById('chart-4'));
    var chart_5 = echarts.init(document.getElementById('chart-5'));
    var chart_6 = echarts.init(document.getElementById('chart-6'));
    // 使用刚指定的配置项和数据显示图表。
    chart_1.setOption(option_chart_1);
    chart_2.setOption(option_chart_2);
    chart_3.setOption(option_chart_3);
    chart_4.setOption(option_chart_4);
    chart_5.setOption(option_chart_5);
    chart_6.setOption(option_chart_6);

}
