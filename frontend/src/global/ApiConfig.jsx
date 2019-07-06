import Request from "./Request";
import Config  from './Config'

const server = Config.serverProtocal+'://'+Config.serverHost+':'+Config.serverPort;
const apiServer = server+'/api'
const fileUploadServer = server+'/imagesUpload';
const apiServerPrefix = apiServer

const API = {
    //登录
    login:{method:'post',url:server+'/login'},
    //退出
    logout:{method:'post',url:server+'/logout'},
    //个人中心
    me:{
        myInfo:{ //获取我的个人信息
            method:'post',url:apiServer+'/user/info'
        },
        updateInfo:{//更新我的个人信息
            method:'post',url:apiServer+'/user/updateInfo'
        }   
    },
    //医院管理员（基本信息管理）
    bacisInfoManagement:{
            //科室管理
        department:{
            all:{//获得全部的科室信息
                method:'get', url:apiServer+'/departmentManage/getAll'
            },
            add:{//添加科室
                method:'post',url:apiServer+'/departmentManage/add'
            },
            delete:{//删除科室
                method:'post',url:apiServer+'/departmentManage/delete'
            },
            update:{//更新科室
                method:'post',url:apiServer+'/departmentManage/update'
            },
            import:{//批量导入
                method:'post',url:apiServer+'/departmentManage/import'
            }
        }, //用户管理
        user:{
            add:{ //添加用户
                method:'post',url:apiServer+'/userManagement/add'
            },
            delete:{ //删除用户
                method:'post',url:apiServer+'/userManagement/delete'
            },
            update:{ //更新用户
                method:'post',url:apiServer+'/userManagement/update'
            },
            all:{ //获取全部用户
                method:'get', url:apiServer+'/userManagement/all'
            },
            import:{//批量导入
                method:'post',url:apiServer+'/userManagement/import'
            }
        }, //结算方式管理
        settlement: {
            all:{ //获取结算方式
                method:'get', url:apiServer+'/settlementCategoryManagement/all'
            },
            add:{ //增加结算方式
                method:'post',url:apiServer+'/settlementCategoryManagement/add'
            },
            delete:{ //删除结算方式
                method:'post',url:apiServer+'/settlementCategoryManagement/delete'
            },
            update:{ //修改结算方式
                method:'post',url:apiServer+'/settlementCategoryManagement/update'
            },
        },//挂号等级管理
        registrationLevel: {
            all:{  //获取所有挂号等级
                method:'get', url:apiServer+'/registrationLevelManagement/all'
            },
            add:{  //增加挂号等级
                method:'post',url:apiServer+'/registrationLevelManagement/add'
            },
            delete:{  //删除挂号等级
                method:'post',url:apiServer+'/registrationLevelManagement/delete'
            },
            update:{ //修改挂号等级
                method:'post',url:apiServer+'/registrationLevelManagement/update'
            }
        },//非药品项目管理
        nonDrugItem: {
            all:{ //获得所有的非药品项目
                method:'get', url:apiServer+'/nonDrugChargeItemManagement/all'
            },
            add:{  //增加非药品项目
                method:'post',url:apiServer+'/nonDrugChargeItemManagement/add'
            },
            delete:{  //删除非药品项目
                method:'post',url:apiServer+'/nonDrugChargeItemManagement/delete'
            },
            update:{ //修改非药品项目
                method:'post',url:apiServer+'/nonDrugChargeItemManagement/update'
            },
            import:{//批量导入
                method:'post',url:apiServer+'/nonDrugChargeItemManagement/import'
            }
        },
        //医生排班管理
    schedulingInfoManagement:{
        //获取班次信息
        getShiftInfo:{
            method:'get',
            url:apiServerPrefix+'/doctorSchedulingShiftManagement/all'
        },
        //添加班次信息
        addShiftInfo:{
            method:'post',
            url:apiServerPrefix+'/doctorSchedulingShiftManagement/add'
        },
        //删除班次信息
        deleteShiftInfo:{
            method:'post',
            url:apiServerPrefix+'/doctorSchedulingShiftManagement/delete'
        },
        //更新班次信息
        updateShiftInfo:{
            method:'post',
            url:apiServerPrefix+'/doctorSchedulingShiftManagement/update'
        },
        //获取人员信息
        getPersonnelInfo:{
            method:'get',
            url:apiServerPrefix+'/doctorSchedulingManagement/all'
        },
        //添加人员信息
        addPersonnelInfo:{
            method:'post',
            url:apiServerPrefix+'/doctorSchedulingManagement/add'
        },
        //删除人员信息
        deletePersonnelInfo:{
            method:'post',
            url:apiServerPrefix+'/doctorSchedulingManagement/delete'
        },
        //更新人员信息
        updatePersonnelInfo:{
            method:'post',
            url:apiServerPrefix+'/doctorSchedulingManagement/update'
        },
        //查找AddForm信息
        getPersonnelAddInfo:{
            method:'post',
            url:apiServerPrefix+'/doctorSchedulingManagement/addPersonnelTable'
        },
        //查找AddForm信息
        getPersonnelAddNameInfo:{
            method:'post',
            url:apiServerPrefix+'/doctorSchedulingManagement/addNamePersonnelTable'
        },
        //选择排班人员
        chooseDoctor:{
            method:'post',
            url:apiServerPrefix+'/doctorWorkforceManagement/choose'
        },
        //获取排班信息
        getScheduleInfo:{
            method:'get',
            url:apiServerPrefix+'/doctorWorkforceManagement/all'
        },
        //更新排班信息
        updateScheduleInfo:{
            method:'get',
            url:apiServerPrefix+'/doctorWorkforceManagement/update'
        },
        //添加排班信息
        addScheduleInfo:{
            method:'post',
            url:apiServerPrefix+'/doctorWorkforceManagement/add'
        },
        //获取全部排班
        getAllScheduleInfo:{
            method:'get',
            url:apiServerPrefix+'/doctorWorkforceManagement/getAll'
        },
        //查找时间冲突
        findTimeConflict:{
            method:'post',
            url:apiServerPrefix+'/doctorWorkforceManagement/time'
        },
        //添加一行排班信息
        addScheduleRowInfo:{
            method:'post',
            url:apiServerPrefix+'/doctorWorkforceManagement/addRow'
        },
        //查找addRow冲突
        findAddRowConflict:{
            method:'post',
            url:apiServerPrefix+'/doctorWorkforceManagement/addRowConflict'
        },
        //删除人员信息
        deleteScheduleRowInfo:{
            method:'post',
            url:apiServerPrefix+'/doctorWorkforceManagement/delete'
        },
        //查找AddForm信息
        getAddInfo:{
            method:'post',
            url:apiServerPrefix+'/doctorWorkforceManagement/addTable'
        },
        //查找AddForm信息
        getAddInfoByID:{
            method:'post',
            url:apiServerPrefix+'/doctorWorkforceManagement/addTableByID'
        },
        //覆盖排班信息
        overwriteScheduleInfo:{
            method:'post',
            url:apiServerPrefix+'/doctorWorkforceManagement/overwrite'
        },
        //删除覆盖的排班信息
        deleteOverwriteInfo:{
            method:'post',
            url:apiServerPrefix+'/doctorWorkforceManagement/deleteOverwrite'
        }
    },
        //诊断目录管理
        diagnoseDirectory: {
            allClassification:{//获得全部的分类
                method:'get',url:apiServer+'/diagnoseDirectoryManagement/allClassification'
            },
            searchAllByClassificationId:{//根据分类的id获取所有疾病
                method:'post',url:apiServer+'/diagnoseDirectoryManagement/searchAllByClassificationId'
            },
            update:{//更新
                method:'post',url:apiServer+'/diagnoseDirectoryManagement/update'
            },
            add:{//添加
                method:'post',url:apiServer+'/diagnoseDirectoryManagement/add'
            },
            delete:{//删除
                method:'post',url:apiServer+'/diagnoseDirectoryManagement/delete'
            },
            import:{//批量导入
                method:'post',url:apiServer+'/diagnoseDirectoryManagement/import'
            }
        }
    },
    //医院收费员（门诊缴费挂号工作站）
    outpatientWorkstation:{
        //门诊挂号
        registration:{
            //初始化面板信息
            init:{
                method:'get',
                url:apiServer+"/outpatientRegistration/init"
            },//同步医生信息
            syncDoctorList:{
                method:'post',
                url:apiServer+'/outpatientRegistration/syncDoctorList'
            },//计算费用
            calculateFee:{
                method:'post',
                url:apiServer+'/outpatientRegistration/calculateFee'
            },//确认挂号
            confirmRegistration:{
                method:'post',
                url:apiServer+'/outpatientRegistration/confirm',
                //mocky:"http://www.mocky.io/v2/5cfb42df300000f6030a8afb"
            },//退号
            withdrawNumber:{
                method:'post',
                url:apiServer+'/outpatientRegistration/withdrawNumber'
            },//搜索用户挂号信息
            searchRegistration:{
                method:'post',
                //mocky:'http://www.mocky.io/v2/5cfcbea13200007800ccd406',
                url:apiServer+'/outpatientCharge/registrationByRecordId'
            }
        },
        //门诊收费
        outpatientCharge:{
            //通过病历号, 获取收费项目列表（待缴费）,
            getChargeItems:{
                method:'post',
                //mocky:'http://www.mocky.io/v2/5cfe6431320000660045f07a',
                url:apiServer+'/outpatientCharge/getChargeItems'
            },//根据病历号，获得挂号信息(同上)
            getRegistrationInfo:{
                method:'post',
                //mocky:'http://www.mocky.io/v2/5cfcbea13200007800ccd406',
                url:apiServer+'/outpatientCharge/registrationByRecordId'
            },//交费
            charge:{
                method:'post',
                //mocky:'http://www.mocky.io/v2/5cfcbf4b3200005900ccd40a',
                url:apiServer+'/outpatientCharge/charge'
            },//退费
            withdraw:{
                method:'post',
                //mocky:'http://www.mocky.io/v2/5cfcbf4b3200005900ccd40a',
                url:apiServer+'/outpatientCharge/withdraw'
            },//通过病历号, 获取收费项目列表（已缴费）,
            getChargedItems:{
                method:'post',
                //mocky:'http://www.mocky.io/v2/5cfe6431320000660045f07a',
                url:apiServer+'/outpatientCharge/getHistoryChargeItems'
            }
        },
        //门诊日结
        dailyCollect:{
            //初始化面板
            getHistoryList:{
                method:'get',
                //mocky:'http://www.mocky.io/v2/5cfe7ce9320000660045f137',
                url:apiServer+'/dailyCollect/list'
            },
            //日结详细信息
            dailyCollectDetail:{
                method:'post',
                //mocky:'http://www.mocky.io/v2/5cfe5bfc3200004f0045f047',
                url:apiServer+'/dailyCollect/detail'
            },
            //日结请求
            dailyCollectRequest:{
                method:'post',
                //mocky:'http://www.mocky.io/v2/5cfe5c3c320000660045f04b',
                url:apiServer+'/dailyCollect/collect'
            }
        },
    },
    //门诊医生
    outpatientDoctor:{
        //门诊病历管理
        medicalRecord:{
            //获取全部中医诊断和西医诊断
            allDiagnoseDiseases:{
                url:apiServer+'/medicalRecord/allDiagnoseDiseases',
                method:'post'
            },
            //同步病人列表
            getPatientList:{
                //mocky:'http://www.mocky.io/v2/5d008c143200005f00f9d582',
                url:apiServer+'/medicalRecord/getPatientList',
                method:'post'
            },
            //根据ID查询一个病人的挂号信息,似乎是药品取药那里用到 !!!!
            //registrationInfo:{
            //    //mocky:'http://www.mocky.io/v2/5cffbab83200006100eac96a',
            //    url:apiServer+'/medicalRecord/medicalRecordHistory',
            //    method:'post'
            //},
            //历史病历 
            historyMedicalRecordList:{
                method:'post',
                //mocky:'http://www.mocky.io/v2/5d03185330000051001f4a44',
                url:apiServer+'/medicalRecord/allHistoryMedicalRecord'
            },//获取（创建）病历
            get:{
                method:'post',
                //mocky:'http://www.mocky.io/v2/5d0ccd313500006200b89b05',
                url:apiServer+'/medicalRecord/getMedicalRecord'
            },//更新(暂存)病历
            update:{
                method:'post',
                //mocky:'http://www.mocky.io/v2/5cfcbf4b3200005900ccd40a',
                url:apiServer+'/medicalRecord/updateMedicalRecord'
            },//保存病历
            save:{
                method:'post',
                //mocky:'http://www.mocky.io/v2/5cfcbf4b3200005900ccd40a',
                url:apiServer+'/medicalRecord/saveMedicalRecord'
            },//确诊提交病历
            confirm:{
                method:'post',
                //mocky:'http://www.mocky.io/v2/5cfcbf4b3200005900ccd40a',
                url:apiServer+'/medicalRecord/confirmMedicalRecord'
            }
        },
        //病历模板
        medicalRecordTemplate:{
            //获取全部病历模板
            list:{
                //mocky:'http://www.mocky.io/v2/5d030d5c30000055001f4a1f',
                url:apiServer+'/medicalRecordTemplate/list',
                method:'post'
            },//获得模板详细
            detail:{
                //mocky:'http://www.mocky.io/v2/5d030bc630000060001f4a1e',
                url:apiServer+'/medicalRecordTemplate/detail',
                method:'post'
            },//创建模板
            create:{
                //mocky:'http://www.mocky.io/v2/5cfe5c3c320000660045f04b',
                url:apiServer+'/medicalRecordTemplate/create',
                method:'post'
            },//更新模板
            update:{
                //mocky:'http://www.mocky.io/v2/5cfe5c3c320000660045f04b',
                url:apiServer+'/medicalRecordTemplate/update',
                method:'post'
            },//删除模板
            delete:{
                //mocky:'http://www.mocky.io/v2/5cfe5c3c320000660045f04b',
                url:apiServer+'/medicalRecordTemplate/delete',
                method:'post'
            }
        },
        //诊断模板
        diagnoseTemplate:{
            //获取全部诊断模板
            list:{
                //mocky:'http://www.mocky.io/v2/5d05088d3200004b00d78c1b',
                url:apiServer+'/diagnoseTemplate/list',
                method:'post'
            },//获得模板详细
            detail:{
                //mocky:'http://www.mocky.io/v2/5d0506743200001400d78c18',
                url:apiServer+'/diagnoseTemplate/detail',
                method:'post'
            },//创建模板
            create:{
                //mocky:'http://www.mocky.io/v2/5cfe5c3c320000660045f04b',
                url:apiServer+'/diagnoseTemplate/create',
                method:'post'
            },//更新模板
            update:{
                //mocky:'http://www.mocky.io/v2/5cfe5c3c320000660045f04b',
                url:apiServer+'/diagnoseTemplate/update',
                method:'post'
            },//删除模板
            delete:{
                //mocky:'http://www.mocky.io/v2/5cfe5c3c320000660045f04b',
                url:apiServer+'/diagnoseTemplate/delete',
                method:'post'
            }
        },
        //检查 检验 处置
        IAD:{
            //根据类型获取所有的可选项目
            allItems:{
                //mocky:' http://www.mocky.io/v2/5d078bcb30000086000521a5',
                url:apiServer+'/exam/allItems',
                method:'post'
            },//创建
            create:{
                //mocky:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',
                url:apiServer+'/exam/create',
                method:'post'
            },//删除
            delete:{
                //mocky:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',
                url:apiServer+'/exam/delete',
                method:'post'
            },//更新
            update:{
                //mocky:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',
                url:apiServer+'/exam/update',
                method:'post'
            },//发送
            send:{
                //mocky:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',
                url:apiServer+'/exam/send',
                method:'post'
            },
            //作废
            cancel:{
                //mocky:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',
				url: apiServer+'/exam/cancel',
                method:'post'
            },//获取当前开具的列表
            list:{
                //mocky:'http://www.mocky.io/v2/5d0cd0013500005b00b89b18',
				url: apiServer+'/exam/list',
                method:'post'
            }
        },
        //检查 检验 处置的组套（模板）
        IADTemplate:{
            //创建
            create:{
                //mocky:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',
				url: apiServer+'/examTemplate/create',
                method:'post'
            },//删除
            delete:{
                //mocky:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',
				url: apiServer+'/examTemplate/delete',
                method:'post'
            },//更新
            update:{
                //mocky:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',
				url: apiServer+'/examTemplate/update',
                method:'post'
            },//获取当前全部组套
            all:{
                //mocky:'http://www.mocky.io/v2/5d0795f13400000e005d938a',
				url: apiServer+'/examTemplate/all',
                method:'post'
            },//获取详细信息
            detail:{
                //mocky:'http://www.mocky.io/v2/5d0885a73400004d005d978f',
				url: apiServer+'/examTemplate/detail',
                method:'post'
            }
        },
        //处方(成药 草药 医技补录)
        prescription:{
            //获取所有的药品
            allDrugs:{
                //mocky:'http://www.mocky.io/v2/5d089c6d34000059005d989d',
				url: apiServer+'/prescription/allDrugs',
                method:'post',
            },//获取全部的处方
            allPrescription:{
                //mocky:'http://www.mocky.io/v2/5d08f5a43400000e00d82cb6',
				url: apiServer+'/prescription/allPrescription',
                method:'post'
            },//创建处方
            create:{
                //mocky:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',
				url: apiServer+'/prescription/create',
                method:'post'
            },//暂存（更新）处方
            update:{
                //mocky:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',
				url: apiServer+'/prescription/update',
                method:'post'
            },//发送处方
            send:{
                //mocky:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',
				url: apiServer+'/prescription/submit',
                method:'post'
            },//删除处方
            delete:{
                //mocky:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',
				url: apiServer+'/prescription/delete',
                method:'post'
            },//作废处方
            cancel:{
                //mocky:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',
				url: apiServer+'/prescription/cancel',
                method:'post'
            }
        },
        prescriptionTemplate:{
            //获取全部的组套
            list:{
                //mocky:'http://www.mocky.io/v2/5d08b2e434000064985d996e',
				url: apiServer+'/prescriptionTemplate/list',
                method:'post'
            },//创建组套
            create:{
                //mocky:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',
				url: apiServer+'/prescriptionTemplate/create',
                method:'post'
            },//组套详情
            detail:{
                //mocky:'http://www.mocky.io/v2/5d0b481e2f00007300e3ef49',
				url: apiServer+'/prescriptionTemplate/detail',
                method:'post'
            },//删除组套
            delete:{
                //mocky:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',
				url: apiServer+'/prescriptionTemplate/delete',
                method:'post'
            },//更新组套
            update:{
                //mocky:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',
				url: apiServer+'/prescriptionTemplate/update',
                method:'post'
            }
        },
        //患者费用查询
        patientFee:{
            //获取全部费用
            historyChargeItems:{
                //mocky:'http://www.mocky.io/v2/5d099fe53400005e29d82f11',
				url: apiServer+'/outpatientCharge/historyChargeItems',
                method:'post'
            }
        }
    },
    //医技科室
    doctorOfTechnology:{
        //其他见上文 处方管理 
        //执行检查 检验 处置
        IADExcute:{
            //根据姓名/身份证/医保/病历号 获取挂号列表 
            searchRegistration:{
                //mocky:'http://www.mocky.io/v2/5d0aedb92f00002800e3ed41',
				url: apiServer+'/examExcute/searchRegistration',
                method:'post'
            },//获取可执行项目列表（包含未缴费）
            allExcuteProject:{
                //mocky:'http://www.mocky.io/v2/5d0b32e82f00007000e3ee82',
				url: apiServer+'/examExcute/allExam',
                method:'post'
            },//登记
            register:{
                //mocky:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',
				url: apiServer+'/examExcute/register',
                method:'post'
            },//录入结果
            submitResult:{
                //mocky:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',
				url: apiServer+'/examExcute/submitResult',
                method:'post'
            },//获取结果
            getResult:{
                //mocky:'http://www.mocky.io/v2/5d0cd6ec3500000d00b89b37',
				url: apiServer+'/examExcute/getResult',
                method:'post'
            }
        }
    },
    //药房
    pharmacyWorkStation:{
        //药品目录管理
        drugInfoManagement:{
            //增加药品信息
            add:{
                method:'post',
                //mocky: 'http://www.mocky.io/v2/5cff0e4b3200004d0045f2d5',
				url: apiServer+'/drugInfoManagement/add'
            },//删除药品信息
            delete:{
                method:'post',
                //mocky:'http://www.mocky.io/v2/5cff0e4b3200004d0045f2d5',
				url: apiServer+'/drugInfoManagement/delete'
            },//更新药品信息
            update:{
                method:'post',
                //mocky:'http://www.mocky.io/v2/5cff0e4b3200004d0045f2d5',
				url: apiServer+'/drugInfoManagement/update'
            },//全部药品信息
            all:{
                method:'post',
                //mocky:'http://www.mocky.io/v2/5cff0f0a3200004e0045f2d9',
				url: apiServer+'/drugInfoManagement/all'
            },//根据药品名字模糊搜索药品
            getDrugInfoByName:{
                method:'post',
                url:apiServer+'/drugInfoManagement/getDrugInfoByName'
            }
        },
        //收发药
        drugDispatcher:{
            //可发药表
            dispenseList:{
                method:'post',
                url:apiServer+'/drugDispense/list',
                //mocky:'http://www.mocky.io/v2/5d021de03100003400ab2c85'
            },//可退药表
            withdrawableList:{
                method:'post',
                //mocky:'http://www.mocky.io/v2/5d021de03100003400ab2c85',
				url: apiServer+'/drugWithdrawal/list'
            },//已经退药表
            withdrawedList:{
                method:'post',
                //mocky:'http://www.mocky.io/v2/5d021de03100003400ab2c85',
				url: apiServer+'/drugDispense/withdrawHistory'
            },//发药
            dispenseSubmit:{
                method:'post',
                //mocky:'http://www.mocky.io/v2/5cfcbf4b3200005900ccd40a',
				url: apiServer+'/drugDispense/submit'
            },//退药
            withdrawSubmit:{
                method:'post',
                //mocky:'http://www.mocky.io/v2/5cfcbf4b3200005900ccd40a',
				url: apiServer+'/drugWithdrawal/submit'
            }
        }
    },
    //财务处统计
    financialAdmin:{
        //收费项目管理
        expenseClassification:{
            all:{//获得全部的收费项目
                method:'get', 
                //mocky:'http://www.mocky.io/v2/5d09dc833400001229d8303f',
				url: apiServer+'/expenseClassificationManage/all'
            },
            add:{//添加收费项目
                method:'post',
                //mocky:'http://www.mocky.io/v2/5d09dca13400005e29d83040',
				url: apiServer+'/expenseClassificationManage/add'
            },
            delete:{//删除收费项目
                method:'post',
                //mocky:'http://www.mocky.io/v2/5d09dca13400005e29d83040',
				url: apiServer+'/expenseClassificationManage/delete'
            },
            update:{//更新收费项目
                method:'post',
                //mocky:'http://www.mocky.io/v2/5d09dca13400005e29d83040',
				url: apiServer+'/expenseClassificationManage/update'
            }
        },
        //日结核对
        dailyReportCheck:{
            //初始化，加载基本数据(收费员名单)
            init:{
                method:'post',
                //mocky:'http://www.mocky.io/v2/5d0c37233500004a00b896f6',
				url: apiServer+'/outpatientDailyReportCheck/init',
            },//获取报告
            getReport:{
                method:'post',
                //mocky:'http://www.mocky.io/v2/5d0c37d93500004a00b896f9',
				url: apiServer+'/outpatientDailyReportCheck/getReport'
            },//财务入库
            confirmCheck:{
                method:'post',
                //mocky:'http://www.mocky.io/v2/5d0c38b23500002d00b896fe',
				url: apiServer+'/outpatientDailyReportCheck/confirmCheck'
            },//历史日结
            history:{
                method:'post',
                //mocky:'http://www.mocky.io/v2/5d0c394e3500005100b89700',
				url: apiServer+'/outpatientDailyReportCheck/history'
            }
        },
        workloadStatistic:{
            //部门工作量统计
            department:{
                method:'post',
                //mocky:'http://www.mocky.io/v2/5d0c42803500002d00b8971c',
				url: apiServer+'/workloadStatistic/departmentStatistic'
            },
            //个人工作量统计
            personal:{
                method:'post',
				url: apiServer+'/workloadStatistic/userStatistic'
            },
            //费用类型工作量统计
            typeStatistic:{
                method:'post',
				url: apiServer+'/workloadStatistic/typeStatistic'
            },

            //医生统计(新增)
            outpatientDoctor:{
                method:"post",
                url:apiServer+'/workloadStatistic/outpatientDoctor'
            },
            //医技医生统计(新增)
            doctorOfTechnology:{
                method:"post",
                url:apiServer+'/workloadStatistic/doctorOfTechnology'
            }
        }
    },


    
    //请求
    request:(api,reqData={})=>{return new Request(api,reqData);},
    //文件服务器地址
    fileUploadServer:fileUploadServer
}

export default API;

