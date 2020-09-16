<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/js.jsp"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title></title>
</head>
<link href="${ctx}/plugins/assets/global/plugins/ztree/metroStyle/metroStyle.css" rel="stylesheet" type="text/css"/>
<link href="${ctx}/plugins/assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.css" rel="stylesheet" type="text/css"/>
<link href="${ctx}/plugins/assets/global/plugins/bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/plugins/assets/global/plugins/jquery-multi-select/css/multi-select.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/plugins/assets/global/plugins/bootstrap-daterangepicker/daterangepicker.min.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/plugins/assets/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css" />
<style>
    .ztree * {
        font-size: 14px;
        margin: 0;
        padding: 0;
    }
    .inputtree{
        display:none;
        position: absolute;
        background-color: white;
        z-index: 9999;
        max-height: 300px;
        overflow: auto;
    }
    input.search-input{
        box-sizing: border-box;
        -moz-box-sizing:border-box;
        width: 100%;
        margin-bottom: 5px;
        height: auto;
    }
    .pageRow{
        width: 80%;
    }
    .datess{text-align: center;cursor:pointer}
    /*.active{background-color: #36c6d3}*/
    li a{ color:black}
</style>
<script src="${ctx}/javascript/plot/echarts.js"></script>
<script>

</script>
<body class="page-content">
    <div class="my-col-md">
        <div class="portlet light">
            <%--<div class="portlet-title" >
            </div>--%>
            <div class="portlet-body flip-scroll">
                <div class="row">
                    <div class="col-md-12 col-lg-12 col-sm-12" id="itemdiv">
                        <ul class="nav nav-tabs" style="background-color: #ededed">
                            <li role="presentation" class="active"><a href="#" data-toggle="tab" index="0" kmbm="A007" declare="财政总收入">财政总收入变化图表</a></li>
                            <li role="presentation"><a href="#" data-toggle="tab" index="1" kmbm="A027" declare="税收收入">税收收入变化图表</a></li>
                            <li role="presentation"><a href="#" data-toggle="tab" index="2" kmbm="A028" declare="非税收收入">非税收收入变化图表</a></li>
                            <li role="presentation"><a href="#" data-toggle="tab" index="3" kmbm="A029" declare="增值税">增值税变化图表</a></li>
                            <li role="presentation"><a href="#" data-toggle="tab" index="4" kmbm="A030" declare="消费税">消费税变化图表</a></li>
                            <li role="presentation"><a href="#" data-toggle="tab" index="5" kmbm="A032" declare="企业所得税">企业所得税变化图表</a></li>
                            <li role="presentation"><a href="#" data-toggle="tab" index="6" kmbm="10119" declare="契税">契税变化图表</a></li>
                            <li role="presentation"><a href="#" data-toggle="tab" index="7" kmbm="H001" declare="工业税收">工业税收变化图表</a></li>
                            <li role="presentation"><a href="#" data-toggle="tab" index="8" kmbm="H002" declare="建筑安装业税收">建筑安装业税收变化图表</a></li>
                            <li role="presentation"><a href="#" data-toggle="tab" index="9" kmbm="H003" declare="交通运输业税收">交通运输业税收变化图表</a></li>
                            <li role="presentation"><a href="#" data-toggle="tab" index="10" kmbm="H004" declare="房地产业税收">房地产业税收变化图表</a></li>
                            <li role="presentation"><a href="#" data-toggle="tab" index="11" kmbm="A027" declare="税比">税比变化图表</a></li>
                            <li role="presentation"><a href="#" data-toggle="tab" index="12" kmbm="A001" declare="一般公共预算收入">一般公共预算收入变化图表</a></li>
                            <li role="presentation"><a href="#" data-toggle="tab" index="13" kmbm="A024" declare="一般公共预算支出">一般公共预算支出变化图表</a></li>

                        </ul>
                    </div>
                </div>
                <div style="width: 100%;height: 70%;" id="test1" ></div>
                <div style="width: 100%;height: 70%;" id="test2" ></div>
            </div>
        </div>
    </div>

</body>
<script>
    $(function () {
        $("li[role=\"presentation\"]").on("click",function () {
            var index = $(this).find("a").attr("index");
            var kmbm = $(this).find("a").attr("kmbm");
            var declare=$(this).find("a").attr("declare");
            var type= kmbm=="A024"?"支出":"收入";
            load_plot(kmbm,declare,type,index );
        });
    });
    var this_year ;
    var last_year;
    var this_year_list = new Array();
    var last_year_list = new Array();
    var this_year_tb_list = new Array();
    var last_year_tb_list = new Array();
    var lj_last_year_row;
    var lj_this_year_row;
    var lj_bfb_last_year_row;
    var lj_bfb_this_year_row;
    //分月统计变量
    var this_year_dy_list = new Array();
    var last_year_dy_list = new Array();
    var this_year_dy_tb_list = new Array();
    var last_year_dy_tb_list = new Array();
    var dy_last_year_row;
    var dy_this_year_row;
    var dy_bfb_last_year_row;
    var dy_bfb_this_year_row;

    function addrow(arr){
           var commontr="";
            for(var i = 0 ;i<arr.length;i++){
                commontr+="{tc|"+arr[i]+"}";
            }
            return commontr;
      /*  '{text_2019|2019收入}{tc|32.01}{tc|23.49}{tc|22.42}{tc|23.69}{tc|20.58}{tc|22.31}{tc|15.58}{tc|10.37}{tc|19.22}{tc|12.73}{tc|5.04}{tc|5.28}',
        '{text_2020|2020收入}{tc|34.04}{tc|22.34}{tc|14.31}{tc|21.44}{tc|21.25}{tc|20.14}{tc|19.39}{tc|}{tc|}{tc|}{tc|}{tc|}',
            '{text_2019_bfb|2019增减%}{tc|13.3}{tc|9.0}{tc|14.0}{tc|8.9}{tc|-2.9}{tc|-8.1}{tc|16.9}{tc|-17.2}{tc|6.8}{tc|5.1}{tc|3.4}{tc|44.7}',
            '{text_2020_bfb|2020增减%}{tc|6.3}{tc|-4.9}{tc|-36.2}{tc|-9.5}{tc|-3.3}{tc|-9.8}{tc|22.6}{tc|}{tc|}{tc|}{tc|}{tc|    }'*/
    }
    var plot_obj={
        "load":load_plot
    }
    load_plot("A007","财政总收入","收入",0);
    function load_plot(kmbm,declare,type,index){
        $.ajax({
            url:ctx+ "/plot/canvos?kmbm="+kmbm+"&index="+index,
            type : "POST",
            dataType : "json",
            async:false,
            success: function (data){
                if(data.result=="success"){
                    this_year = data.this_year;
                    last_year = data.last_year;
                    this_year_list = data.this_year_Array;
                    lj_this_year_row = "{text_2020|"+this_year+type+"}"+ addrow(this_year_list);
                    last_year_list = data.last_year_Array;
                    lj_last_year_row = "{text_2019|"+last_year+type+"}"+ addrow(last_year_list);
                    this_year_tb_list = data.this_year_tb_Array;
                    lj_bfb_this_year_row = "{text_2020_bfb|"+this_year+"增减%}"+ addrow(this_year_tb_list);
                    last_year_tb_list = data.last_year_tb_Array;
                    lj_bfb_last_year_row = "{text_2019_bfb|"+last_year+"增减%}"+ addrow(last_year_tb_list);

                    //分月统计
                    this_year_dy_list = data.this_year_dy_Array;
                    dy_this_year_row = "{text_2020|"+this_year+type+"}"+ addrow(this_year_dy_list);
                    last_year_dy_list = data.last_year_dy_Array;
                    dy_last_year_row = "{text_2019|"+last_year+type+"}"+ addrow(last_year_dy_list);
                    this_year_dy_tb_list = data.this_year_dy_tb_Array;
                    dy_bfb_this_year_row = "{text_2020_bfb|"+this_year+"增减%}"+ addrow(this_year_dy_tb_list);
                    last_year_dy_tb_list = data.last_year_dy_tb_Array;
                    dy_bfb_last_year_row = "{text_2019_bfb|"+last_year+"增减%}"+ addrow(last_year_dy_tb_list);

                    var chart1 = echarts.init(document.getElementById("test1"));
                    var chart2 = echarts.init(document.getElementById('test2'));
                    var ROOT_PATH='https://echarts-www.cdn.bcebos.com/examples'
                    var weatherIcons = {
                        'Sunny': ROOT_PATH + '/data/asset/img/weather/sunny_128.png',
                        'Cloudy': ROOT_PATH + '/data/asset/img/weather/cloudy_128.png',
                        'Showers': ROOT_PATH + '/data/asset/img/weather/showers_128.png'
                    };
                    var option_dy = {
                        title:
                            {
                                top: '40',
                                text: index=="11"?"财政总收入税比分月情况":declare+"分月情况",
                                left: 700,
                                align:"center",
                                textStyle:{
                                    color:"#3a8ef1"
                                }
                                /*  subtextStyle: {
                                      align: 'left',
                                      color: '#333fff',
                                      fontSize: 12,
                                  },*/
                                //   subtext: '单\n位\n:\n亿\n元'//   \n换行
                            }
                        ,
                        grid: {
                            bottom: 200
                        },
                        /* backgroundColor: {
                             type: 'linear',
                             x: 0,
                             y: 0,
                             x2: 0,
                             y2: 1,
                             colorStops: [{
                                 offset: 0, color: 'rgba(70, 131, 254, 0)' // 0% 处的颜色
                             }, {
                                 offset: 1, color: 'rgba(70, 131, 254, 0.5)' // 100% 处的颜色
                             }],
                             global: true // 缺省为 false
                         },*/

                        //提示框
                        tooltip: {
                            trigger: 'axis',
                            axisPointer: {
                                type: 'cross',
                                crossStyle: {
                                    color: '#999'
                                }
                            }
                        },
                        toolbox:{
                            feature:{
                                saveAsImage:{
                                    type:'png',
                                    title:'保存为图片',
                                    name: index=="11"?"财政总收入税比分月情况":declare+"分月情况",
                                    show: true,
                                    position:"center"
                                }
                            }
                        },
                        //图例
                        legend: {
                            data: [
                                {name: last_year+'年'+type},
                                {name: this_year+'年'+type},
                                {name: last_year+'年增减%'},
                                {name: this_year+'年增减%'}
                            ],
                            textStyle: {
                                color: '#333fff',
                                fontSize: 11
                            },
                            y: 'bottom',
                            x: 'center',
                        },
                        xAxis: [
                            {
                                type: 'category',
                                axisLine: {
                                    lineStyle: {
                                        color: '#1F7EFF',
                                        width: 1
                                    }
                                },
                                data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
                                axisPointer: {
                                    type: 'shadow'
                                },
                                axisLabel: {
                                    interval: 0,//横轴信息全部显示
                                    textStyle: {
                                        color: '#333'
                                    },
                                    fontSize: 11,
                                    // rotate:45,//度角倾斜显示
                                    formatter: function (value) {
                                        return value.length > 5 ? value.substring(0, 5) + '...' : value;
                                    }
                                }
                            }
                        ],
                        yAxis: [//这里配置两条Y轴
                            {
                                type: 'value',
                                splitLine: {
                                    show: true,
                                    lineStyle: {
                                        color: '#f8f8f8',
                                        width: 1
                                    }
                                },
                                axisLine: {
                                    lineStyle: {
                                        color: '#1F7EFF',
                                        width: 1
                                    }
                                },
                                axisLabel: {
                                    show: true,
                                    textStyle: {
                                        color: '#333'
                                    },
                                    fontSize: 11,
                                    interval: 'auto',
                                    formatter: '{value}'
                                },
                                name: '单位(亿元)',
                                nameTextStyle: {
                                    color: '#333'
                                }
                            },
                            {
                                type: 'value',
                                splitLine: {
                                    show: true,
                                    lineStyle: {
                                        color: '#fbfbfd',
                                        width: 1
                                    }
                                },
                                axisLine: {
                                    lineStyle: {
                                        color: '#1F7EFF',
                                        width: 1
                                    }
                                },
                                axisLabel: {
                                    show: true,
                                    textStyle: {
                                        color: '#333'
                                    },
                                    fontSize: 11,
                                    interval: 'auto',
                                    formatter: '{value}'
                                },
                                name: '单位（%）',
                                nameTextStyle: {
                                    color: '#333',
                                    fontSize: 11,
                                }
                            }
                        ],
                        series: [
                            {
                                name: last_year+'年'+type,
                                barWidth: '30%',
                                type: 'bar',
                                itemStyle: {
                                    normal: {
                                        lineStyle: {
                                            color: '#c3e5f2'
                                        },
                                        color: '#c3e5f2',
                                    }
                                },
                                label:{
                                    normal:{
                                        show:false,
                                        position:'top'
                                    },
                                },
                                yAxisIndex: 0, //使用的 y 轴的 index，在单个图表实例中存在多个 y轴的时候有用。
                                data: last_year_dy_list
                            },
                            {
                                name: this_year+'年'+type,
                                barWidth: '30%',
                                type: 'bar',
                                itemStyle: {
                                    normal: {
                                        lineStyle: {
                                            color: '#ffd8ec'
                                        },
                                        color: '#ffd8ec',
                                    }
                                },
                                label:{
                                    normal:{
                                        show:false,
                                        position:'top'
                                    }
                                },
                                yAxisIndex: 0,
                                data: this_year_dy_list
                            },
                            {
                                name: last_year+'年增减%',
                                barWidth: '10%',
                                type: 'line',
                                label:{
                                    normal:{
                                        show:false,
                                        position:'top'
                                    }
                                },
                                itemStyle: {
                                    normal: {
                                        lineStyle: {
                                            color: '#E63234'
                                        },
                                        fontSize: 11,
                                        color: '#E63234',
                                    }
                                },
                                symbol: 'circle',
                                symbolSize: 10, //折线点的大小
                                yAxisIndex: 1, ////使用的 y 轴的 index，在单个图表实例中存在多个 y轴的时候有用。
                                data:last_year_dy_tb_list
                            },
                            {
                                name: this_year+'年增减%',
                                barWidth: '30%',
                                label:{
                                    normal:{
                                        show:false,
                                        position:'top'
                                    }
                                },
                                type: 'line',
                                itemStyle: {
                                    normal: {
                                        lineStyle: {
                                            color: '#2b4df1'
                                        },
                                        fontSize: 11,
                                        color: '#2b4df1',
                                    }
                                },
                                symbol: 'circle',
                                symbolSize: 10, //折线点的大小
                                yAxisIndex: 1,
                                data:this_year_dy_tb_list
                            },
                            {
                                type: 'scatter',
                                data: [
                                    {
                                        value: [0,0],
                                        label: {
                                            normal: {
                                                formatter: [

                                                    // '{nf|年份}{tc|1月}{tc|2月}{tc|3月}{tc|4月}{tc|5月}{tc|6月}{tc|7月}{tc|8月}{tc|9月}{tc|10月}{tc|11月}{tc|12月} ',
                                                    /* '{text_2019|2019收入}{tc|32.01}{tc|23.49}{tc|22.42}{tc|23.69}{tc|20.58}{tc|22.31}{tc|15.58}{tc|10.37}{tc|19.22}{tc|12.73}{tc|5.04}{tc|5.28}',
                                                     '{text_2020|2020收入}{tc|34.04}{tc|22.34}{tc|14.31}{tc|21.44}{tc|21.25}{tc|20.14}{tc|19.39}{tc|}{tc|}{tc|}{tc|}{tc|}',
                                                     '{text_2019_bfb|2019增减%}{tc|13.3}{tc|9.0}{tc|14.0}{tc|8.9}{tc|-2.9}{tc|-8.1}{tc|16.9}{tc|-17.2}{tc|6.8}{tc|5.1}{tc|3.4}{tc|44.7}',
                                                     '{text_2020_bfb|2020增减%}{tc|6.3}{tc|-4.9}{tc|-36.2}{tc|-9.5}{tc|-3.3}{tc|-9.8}{tc|22.6}{tc|}{tc|}{tc|}{tc|}{tc|    }'*/
                                                    dy_last_year_row,
                                                    dy_this_year_row,
                                                    dy_bfb_last_year_row,
                                                    dy_bfb_this_year_row
                                                ].join('\n'),

                                            }
                                        }
                                    },
                                ],
                                symbolSize: 1,
                                label: {
                                    normal: {
                                        show: true,
                                        position: [-125, 30],
                                        backgroundColor: '#ffffff',
                                        borderColor: '#555',
                                        borderWidth: 1,
                                        borderRadius: 1,
                                        color: '#000',
                                        fontSize: 14,
                                        rich: {
                                            titleBg: {
                                                backgroundColor: '#000',
                                                height: 30,
                                                borderRadius: [5, 5, 0, 0],
                                                padding: [0, 10, 0, 10],
                                                width: '100%',
                                                color: '#eee'
                                            },
                                            text_2019: {
                                                color: '#333',
                                                //  color: '#c3e5f2',
                                                backgroundColor:'#c3e5f2',
                                                height: 24,
                                                width:45,
                                                align: 'left',
                                                padding: [0, 20, 0, 5],
                                                fontsize:14,
                                            },
                                            text_2020: {
                                                //color: '#ffd8ec',
                                                color: '#333',
                                                backgroundColor:'#ffd8ec',
                                                height: 24,
                                                width:45,
                                                align: 'left',
                                                padding: [0, 20, 0, 5],
                                                fontsize:14,
                                            },
                                            text_2019_bfb: {
                                                //color: '#E63234',
                                                color: '#333',
                                                backgroundColor:'#E63234',
                                                height: 24,
                                                width:45,
                                                align: 'left',
                                                padding: [0, 20, 0, 5],
                                                fontsize:14,
                                            },
                                            text_2020_bfb: {
                                                //  color: '#2b4df1',
                                                color: '#333',
                                                height: 24,
                                                width:45,
                                                backgroundColor:'#2b4df1',
                                                align: 'left',
                                                padding: [0, 20, 0, 5],
                                                fontsize:14,
                                            },
                                            nf: {
                                                color: '#333',
                                                height: 24,
                                                width:45,
                                                align: 'left',
                                                padding: [0, 20, 0, 5],
                                                fontsize:14,
                                            },
                                            tc: {
                                                color: '#333',
                                                height: 24,
                                                borderWidth:1,
                                                //backgroundColor: '#b3c3ca',
                                                align:"center",
                                                borderColor: '#555',
                                                // color:"red",
                                                //width:108,
                                                width:"155%",
                                                border:2,

                                                //padding: [0, 20, 0, 60],
                                                fontsize:14,
                                            },
                                            value: {
                                                width: 20,
                                                padding: [0, 20, 0, 0],
                                                align: 'left'
                                            },
                                            hr: {
                                                borderColor: '#777',
                                                width: '100%',
                                                borderWidth: 0.5,
                                                height: 0
                                            },
                                            sunny: {
                                                height: 30,
                                                align: 'center',
                                                backgroundColor: {
                                                    image: weatherIcons.Sunny
                                                }
                                            },
                                            cloudy: {
                                                height: 30,
                                                align: 'center',
                                                backgroundColor: {
                                                    image: weatherIcons.Cloudy
                                                }
                                            },
                                            showers: {
                                                height: 30,
                                                align: 'center',
                                                backgroundColor: {
                                                    image: weatherIcons.Showers
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        ]
                    };

                    var option_lj = {
                        title:
                            {
                                top: '40',
                                text:index=="11"?"一般公共预算收入税比分月情况":declare+'累计情况',
                                left: 700,
                                align:"center",
                                textStyle:{
                                    color:"#3a8ef1"
                                }
                                /*  subtextStyle: {
                                      align: 'left',
                                      color: '#333fff',
                                      fontSize: 12,
                                  },*/
                                //   subtext: '单\n位\n:\n亿\n元'//   \n换行
                            }
                        ,
                        grid: {
                            bottom: 200
                        },
                        /* backgroundColor: {
                             type: 'linear',
                             x: 0,
                             y: 0,
                             x2: 0,
                             y2: 1,
                             colorStops: [{
                                 offset: 0, color: 'rgba(70, 131, 254, 0)' // 0% 处的颜色
                             }, {
                                 offset: 1, color: 'rgba(70, 131, 254, 0.5)' // 100% 处的颜色
                             }],
                             global: true // 缺省为 false
                         },*/

                        //提示框
                        tooltip: {
                            trigger: 'axis',
                            axisPointer: {
                                type: 'cross',
                                crossStyle: {
                                    color: '#999'
                                }
                            }
                        },
                        toolbox:{
                            feature:{
                                saveAsImage:{
                                    type:'png',
                                    title:'保存为图片',
                                    name:index=="11"?"一般公共预算收入税比分月情况":declare+'累计情况',
                                    show: true,
                                    position:"center"
                                }
                            }
                        },
                        //图例
                        legend: {
                            data: [
                                {name: last_year+'年'+type},
                                {name: this_year+'年'+type},
                                {name: last_year+'年增减%'},
                                {name: this_year+'年增减%'}
                            ],
                            textStyle: {
                                color: '#333fff',
                                fontSize: 11
                            },
                            y: 'bottom',
                            x: 'center',
                        },
                        xAxis: [
                            {
                                type: 'category',
                                axisLine: {
                                    lineStyle: {
                                        color: '#1F7EFF',
                                        width: 1
                                    }
                                },
                                data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
                                axisPointer: {
                                    type: 'shadow'
                                },
                                axisLabel: {
                                    interval: 0,//横轴信息全部显示
                                    textStyle: {
                                        color: '#333'
                                    },
                                    fontSize: 11,
                                    // rotate:45,//度角倾斜显示
                                    formatter: function (value) {
                                        return value.length > 5 ? value.substring(0, 5) + '...' : value;
                                    }
                                }
                            }
                        ],
                        yAxis: [//这里配置两条Y轴
                            {
                                type: 'value',
                                splitLine: {
                                    show: true,
                                    lineStyle: {
                                        color: '#f8f8f8',
                                        width: 1
                                    }
                                },
                                axisLine: {
                                    lineStyle: {
                                        color: '#1F7EFF',
                                        width: 1
                                    }
                                },
                                axisLabel: {
                                    show: true,
                                    textStyle: {
                                        color: '#333'
                                    },
                                    fontSize: 11,
                                    interval: 'auto',
                                    formatter: '{value}'
                                },
                                name: '单位(亿元)',
                                nameTextStyle: {
                                    color: '#333'
                                }
                            },
                            {
                                type: 'value',
                                splitLine: {
                                    show: true,
                                    lineStyle: {
                                        color: '#fbfbfd',
                                        width: 1
                                    }
                                },
                                axisLine: {
                                    lineStyle: {
                                        color: '#1F7EFF',
                                        width: 1
                                    }
                                },
                                axisLabel: {
                                    show: true,
                                    textStyle: {
                                        color: '#333'
                                    },
                                    fontSize: 11,
                                    interval: 'auto',
                                    formatter: '{value}'
                                },
                                name: '单位（%）',
                                nameTextStyle: {
                                    color: '#333',
                                    fontSize: 11,
                                }
                            }
                        ],
                        series: [
                            {
                                name: last_year+'年'+type,
                                barWidth: '30%',
                                type: 'bar',
                                itemStyle: {
                                    normal: {
                                        lineStyle: {
                                            color: '#c3e5f2'
                                        },
                                        color: '#c3e5f2',
                                    }
                                },
                                label:{
                                    normal:{
                                        show:false,
                                        position:'top'
                                    },
                                },
                                yAxisIndex: 0, //使用的 y 轴的 index，在单个图表实例中存在多个 y轴的时候有用。
                                data: last_year_list
                            },
                            {
                                name: this_year+'年'+type,
                                barWidth: '30%',
                                type: 'bar',
                                itemStyle: {
                                    normal: {
                                        lineStyle: {
                                            color: '#ffd8ec'
                                        },
                                        color: '#ffd8ec',
                                    }
                                },
                                label:{
                                    normal:{
                                        show:false,
                                        position:'top'
                                    }
                                },
                                yAxisIndex: 0,
                                data:this_year_list
                            },
                            {
                                name: last_year+'年增减%',
                                barWidth: '10%',
                                type: 'line',
                                label:{
                                    normal:{
                                        show:false,
                                        position:'top'
                                    }
                                },
                                itemStyle: {
                                    normal: {
                                        lineStyle: {
                                            color: '#E63234'
                                        },
                                        fontSize: 11,
                                        color: '#E63234',
                                    }
                                },
                                symbol: 'circle',
                                symbolSize: 10, //折线点的大小
                                yAxisIndex: 1, ////使用的 y 轴的 index，在单个图表实例中存在多个 y轴的时候有用。
                                data:last_year_tb_list
                            },
                            {
                                name: this_year +'年增减%',
                                barWidth: '30%',
                                label:{
                                    normal:{
                                        show:false,
                                        position:'top'
                                    }
                                },
                                type: 'line',
                                itemStyle: {
                                    normal: {
                                        lineStyle: {
                                            color: '#2b4df1'
                                        },
                                        fontSize: 11,
                                        color: '#2b4df1',
                                    }
                                },
                                symbol: 'circle',
                                symbolSize: 10, //折线点的大小
                                yAxisIndex: 1,
                                data: this_year_tb_list
                            },
                            {
                                type: 'scatter',
                                data: [
                                    {
                                        value: [0,0],
                                        label: {
                                            normal: {
                                                formatter: [

                                                    // '{nf|年份}{tc|1月}{tc|2月}{tc|3月}{tc|4月}{tc|5月}{tc|6月}{tc|7月}{tc|8月}{tc|9月}{tc|10月}{tc|11月}{tc|12月} ',
                                                    /*'{text_2019|2019收入}{tc|32.01}{tc|23.49}{tc|22.42}{tc|23.69}{tc|20.58}{tc|22.31}{tc|15.58}{tc|10.37}{tc|19.22}{tc|12.73}{tc|5.04}{tc|5.28}',
                                                    '{text_2020|2020收入}{tc|34.04}{tc|22.34}{tc|14.31}{tc|21.44}{tc|21.25}{tc|20.14}{tc|19.39}{tc|}{tc|}{tc|}{tc|}{tc|}',
                                                    '{text_2019_bfb|2019增减%}{tc|13.3}{tc|9.0}{tc|14.0}{tc|8.9}{tc|-2.9}{tc|-8.1}{tc|16.9}{tc|-17.2}{tc|6.8}{tc|5.1}{tc|3.4}{tc|44.7}',
                                                    '{text_2020_bfb|2020增减%}{tc|6.3}{tc|-4.9}{tc|-36.2}{tc|-9.5}{tc|-3.3}{tc|-9.8}{tc|22.6}{tc|}{tc|}{tc|}{tc|}{tc|    }'*/
                                                    lj_last_year_row,
                                                    lj_this_year_row,
                                                    lj_bfb_last_year_row,
                                                    lj_bfb_this_year_row
                                                ].join('\n'),

                                            }
                                        }
                                    },
                                ],
                                symbolSize: 1,
                                label: {
                                    normal: {
                                        show: true,
                                        position: [-125, 30],
                                        backgroundColor: '#ffffff',
                                        borderColor: '#555',
                                        borderWidth: 1,
                                        borderRadius: 1,
                                        color: '#000',
                                        fontSize: 14,
                                        rich: {
                                            titleBg: {
                                                backgroundColor: '#000',
                                                height: 30,
                                                borderRadius: [5, 5, 0, 0],
                                                padding: [0, 10, 0, 10],
                                                width: '100%',
                                                color: '#eee'
                                            },
                                            text_2019: {
                                                color: '#333',
                                                //  color: '#c3e5f2',
                                                backgroundColor:'#c3e5f2',
                                                height: 24,
                                                width:45,
                                                align: 'left',
                                                padding: [0, 20, 0, 5],
                                                fontsize:14,
                                            },
                                            text_2020: {
                                                //color: '#ffd8ec',
                                                color: '#333',
                                                backgroundColor:'#ffd8ec',
                                                height: 24,
                                                width:45,
                                                align: 'left',
                                                padding: [0, 20, 0, 5],
                                                fontsize:14,
                                            },
                                            text_2019_bfb: {
                                                //color: '#E63234',
                                                color: '#333',
                                                backgroundColor:'#E63234',
                                                height: 24,
                                                width:45,
                                                align: 'left',
                                                padding: [0, 20, 0, 5],
                                                fontsize:14,
                                            },
                                            text_2020_bfb: {
                                                //  color: '#2b4df1',
                                                color: '#333',
                                                height: 24,
                                                width:45,
                                                backgroundColor:'#2b4df1',
                                                align: 'left',
                                                padding: [0, 20, 0, 5],
                                                fontsize:14,
                                            },
                                            nf: {
                                                color: '#333',
                                                height: 24,
                                                width:45,
                                                align: 'left',
                                                padding: [0, 20, 0, 5],
                                                fontsize:14,
                                            },
                                            tc: {
                                                color: '#333',
                                                height: 24,
                                                borderWidth:1,
                                                //backgroundColor: '#b3c3ca',
                                                align:"center",
                                                borderColor: '#555',
                                                // color:"red",
                                                //width:108,
                                                width:"155%",
                                                border:2,

                                                //padding: [0, 20, 0, 60],
                                                fontsize:14,
                                            },
                                            value: {
                                                width: 20,
                                                padding: [0, 20, 0, 0],
                                                align: 'left'
                                            },
                                            hr: {
                                                borderColor: '#777',
                                                width: '100%',
                                                borderWidth: 0.5,
                                                height: 0
                                            },
                                            sunny: {
                                                height: 30,
                                                align: 'center',
                                                backgroundColor: {
                                                    image: weatherIcons.Sunny
                                                }
                                            },
                                            cloudy: {
                                                height: 30,
                                                align: 'center',
                                                backgroundColor: {
                                                    image: weatherIcons.Cloudy
                                                }
                                            },
                                            showers: {
                                                height: 30,
                                                align: 'center',
                                                backgroundColor: {
                                                    image: weatherIcons.Showers
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        ]
                    };
                    chart1.setOption(option_dy);
                    chart2.setOption(option_lj);
                }else {
                    //alert("查询出错");
                }
            }
        });
    }


</script>