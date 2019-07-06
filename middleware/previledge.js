const API = {
    //登录
    login:{allow:[1,2,3,4,5],method:'post',url:'/login'},
    //退出
    logout:{allow:[1,2,3,4,5],method:'post',url:'/logout'},
    //个人中心
    me:{
        myInfo:{ //获取我的个人信息
            allow:[1,2,3,4,5],method:'post',url:'/user/info'
        },
        updateInfo:{//更新我的个人信息
            allow:[1,2,3,4,5],method:'post',url:'/user/updateInfo'
        }   
    },
    //医院管理员（基本信息管理）
    bacisInfoManagement:{
            //科室管理
        department:{
            all:{//获得全部的科室信息
                allow:[1,2,3,4,5],method:'get', url:'/departmentManage/getAll'
            },
            add:{//添加科室
                allow:[1,2,3,4,5],method:'post',url:'/departmentManage/add'
            },
            delete:{//删除科室
                allow:[1,2,3,4,5],method:'post',url:'/departmentManage/delete'
            },
            update:{//更新科室
                allow:[1,2,3,4,5],method:'post',url:'/departmentManage/update'
            },
            import:{//批量导入
                allow:[1,2,3,4,5],method:'post',url:'/departmentManage/import'
            }
        }, //用户管理
        user:{
            add:{ //添加用户
                allow:[1,2,3,4,5],method:'post',url:'/userManagement/add'
            },
            delete:{ //删除用户
                allow:[1,2,3,4,5],method:'post',url:'/userManagement/delete'
            },
            update:{ //更新用户
                allow:[1,2,3,4,5],method:'post',url:'/userManagement/update'
            },
            all:{ //获取全部用户
                allow:[1,2,3,4,5],method:'get', url:'/userManagement/all'
            },
            import:{//批量导入
                allow:[1,2,3,4,5],method:'post',url:'/userManagement/import'
            }
        }, //结算方式管理
        settlement: {
            all:{ //获取结算方式
                allow:[1,2,3,4,5],method:'get', url:'/settlementCategoryManagement/all'
            },
            add:{ //增加结算方式
                allow:[1,2,3,4,5],method:'post',url:'/settlementCategoryManagement/add'
            },
            delete:{ //删除结算方式
                allow:[1,2,3,4,5],method:'post',url:'/settlementCategoryManagement/delete'
            },
            update:{ //修改结算方式
                allow:[1,2,3,4,5],method:'post',url:'/settlementCategoryManagement/update'
            },
        },//挂号等级管理
        registrationLevel: {
            all:{  //获取所有挂号等级
                allow:[1,2,3,4,5],method:'get', url:'/registrationLevelManagement/all'
            },
            add:{  //增加挂号等级
                allow:[1,2,3,4,5],method:'post',url:'/registrationLevelManagement/add'
            },
            delete:{  //删除挂号等级
                allow:[1,2,3,4,5],method:'post',url:'/registrationLevelManagement/delete'
            },
            update:{ //修改挂号等级
                allow:[1,2,3,4,5],method:'post',url:'/registrationLevelManagement/update'
            }
        },//非药品项目管理
        nonDrugItem: {
            all:{ //获得所有的非药品项目
                allow:[1,2,3,4,5],method:'get', url:'/nonDrugChargeItemManagement/all'
            },
            add:{  //增加非药品项目
                allow:[1,2,3,4,5],method:'post',url:'/nonDrugChargeItemManagement/add'
            },
            delete:{  //删除非药品项目
                allow:[1,2,3,4,5],method:'post',url:'/nonDrugChargeItemManagement/delete'
            },
            update:{ //修改非药品项目
                allow:[1,2,3,4,5],method:'post',url:'/nonDrugChargeItemManagement/update'
            },
            import:{//批量导入
                allow:[1,2,3,4,5],method:'post',url:'/nonDrugChargeItemManagement/import'
            }
        },
        //医生排班管理
    schedulingInfoManagement:{
        //获取班次信息
        getShiftInfo:{
            allow:[1,2,3,4,5],method:'get',
            url:apiServerPrefix+'/doctorSchedulingShiftManagement/all'
        },
        //添加班次信息
        addShiftInfo:{
            allow:[1,2,3,4,5],method:'post',
            url:apiServerPrefix+'/doctorSchedulingShiftManagement/add'
        },
        //删除班次信息
        deleteShiftInfo:{
            allow:[1,2,3,4,5],method:'post',
            url:apiServerPrefix+'/doctorSchedulingShiftManagement/delete'
        },
        //更新班次信息
        updateShiftInfo:{
            allow:[1,2,3,4,5],method:'post',
            url:apiServerPrefix+'/doctorSchedulingShiftManagement/update'
        },
        //获取人员信息
        getPersonnelInfo:{
            allow:[1,2,3,4,5],method:'get',
            url:apiServerPrefix+'/doctorSchedulingManagement/all'
        },
        //添加人员信息
        addPersonnelInfo:{
            allow:[1,2,3,4,5],method:'post',
            url:apiServerPrefix+'/doctorSchedulingManagement/add'
        },
        //删除人员信息
        deletePersonnelInfo:{
            allow:[1,2,3,4,5],method:'post',
            url:apiServerPrefix+'/doctorSchedulingManagement/delete'
        },
        //更新人员信息
        updatePersonnelInfo:{
            allow:[1,2,3,4,5],method:'post',
            url:apiServerPrefix+'/doctorSchedulingManagement/update'
        },
        //查找AddForm信息
        getPersonnelAddInfo:{
            allow:[1,2,3,4,5],method:'post',
            url:apiServerPrefix+'/doctorSchedulingManagement/addPersonnelTable'
        },
        //查找AddForm信息
        getPersonnelAddNameInfo:{
            allow:[1,2,3,4,5],method:'post',
            url:apiServerPrefix+'/doctorSchedulingManagement/addNamePersonnelTable'
        },
        //选择排班人员
        chooseDoctor:{
            allow:[1,2,3,4,5],method:'post',
            url:apiServerPrefix+'/doctorWorkforceManagement/choose'
        },
        //获取排班信息
        getScheduleInfo:{
            allow:[1,2,3,4,5],method:'get',
            url:apiServerPrefix+'/doctorWorkforceManagement/all'
        },
        //更新排班信息
        updateScheduleInfo:{
            allow:[1,2,3,4,5],method:'get',
            url:apiServerPrefix+'/doctorWorkforceManagement/update'
        },
        //添加排班信息
        addScheduleInfo:{
            allow:[1,2,3,4,5],method:'post',
            url:apiServerPrefix+'/doctorWorkforceManagement/add'
        },
        //获取全部排班
        getAllScheduleInfo:{
            allow:[1,2,3,4,5],method:'get',
            url:apiServerPrefix+'/doctorWorkforceManagement/getAll'
        },
        //查找时间冲突
        findTimeConflict:{
            allow:[1,2,3,4,5],method:'post',
            url:apiServerPrefix+'/doctorWorkforceManagement/time'
        },
        //添加一行排班信息
        addScheduleRowInfo:{
            allow:[1,2,3,4,5],method:'post',
            url:apiServerPrefix+'/doctorWorkforceManagement/addRow'
        },
        //查找addRow冲突
        findAddRowConflict:{
            allow:[1,2,3,4,5],method:'post',
            url:apiServerPrefix+'/doctorWorkforceManagement/addRowConflict'
        },
        //删除人员信息
        deleteScheduleRowInfo:{
            allow:[1,2,3,4,5],method:'post',
            url:apiServerPrefix+'/doctorWorkforceManagement/delete'
        },
        //查找AddForm信息
        getAddInfo:{
            allow:[1,2,3,4,5],method:'post',
            url:apiServerPrefix+'/doctorWorkforceManagement/addTable'
        },
        //查找AddForm信息
        getAddInfoByID:{
            allow:[1,2,3,4,5],method:'post',
            url:apiServerPrefix+'/doctorWorkforceManagement/addTableByID'
        },
        //覆盖排班信息
        overwriteScheduleInfo:{
            allow:[1,2,3,4,5],method:'post',
            url:apiServerPrefix+'/doctorWorkforceManagement/overwrite'
        },
        //删除覆盖的排班信息
        deleteOverwriteInfo:{
            allow:[1,2,3,4,5],method:'post',
            url:apiServerPrefix+'/doctorWorkforceManagement/deleteOverwrite'
        }
    },
        //诊断目录管理
        diagnoseDirectory: {
            allClassification:{//获得全部的分类
                allow:[1,2,3,4,5],method:'get',url:'/diagnoseDirectoryManagement/allClassification'
            },
            searchAllByClassificationId:{//根据分类的id获取所有疾病
                allow:[1,2,3,4,5],method:'post',url:'/diagnoseDirectoryManagement/searchAllByClassificationId'
            },
            update:{//更新
                allow:[1,2,3,4,5],method:'post',url:'/diagnoseDirectoryManagement/update'
            },
            add:{//添加
                allow:[1,2,3,4,5],method:'post',url:'/diagnoseDirectoryManagement/add'
            },
            delete:{//删除
                allow:[1,2,3,4,5],method:'post',url:'/diagnoseDirectoryManagement/delete'
            },
            import:{//批量导入
                allow:[1,2,3,4,5],method:'post',url:'/diagnoseDirectoryManagement/import'
            }
        }
    },
    //医院收费员（门诊缴费挂号工作站）
    outpatientWorkstation:{
        //门诊挂号
        registration:{
            //初始化面板信息
            init:{
                allow:[1,2,3,4,5],method:'get',
                url:"/outpatientRegistration/init"
            },//同步医生信息
            syncDoctorList:{
                allow:[1,2,3,4,5],method:'post',
                url:'/outpatientRegistration/syncDoctorList'
            },//计算费用
            calculateFee:{
                allow:[1,2,3,4,5],method:'post',
                url:'/outpatientRegistration/calculateFee'
            },//确认挂号
            confirmRegistration:{
                allow:[1,2,3,4,5],method:'post',
                url:'/outpatientRegistration/confirm',
                //mocky:"http://www.mocky.io/v2/5cfb42df300000f6030a8afb"
            },//退号
            withdrawNumber:{
                allow:[1,2,3,4,5],method:'post',
                url:'/outpatientRegistration/withdrawNumber'
            },//搜索用户挂号信息
            searchRegistration:{
                allow:[1,2,3,4,5],method:'post',
                //mocky:'http://www.mocky.io/v2/5cfcbea13200007800ccd406',
                url:'/outpatientCharge/registrationByRecordId'
            }
        },
        //门诊收费
        outpatientCharge:{
            //通过病历号, 获取收费项目列表（待缴费）,
            getChargeItems:{
                allow:[1,2,3,4,5],method:'post',
                //mocky:'http://www.mocky.io/v2/5cfe6431320000660045f07a',
                url:'/outpatientCharge/getChargeItems'
            },//根据病历号，获得挂号信息(同上)
            getRegistrationInfo:{
                allow:[1,2,3,4,5],method:'post',
                //mocky:'http://www.mocky.io/v2/5cfcbea13200007800ccd406',
                url:'/outpatientCharge/registrationByRecordId'
            },//交费
            charge:{
                allow:[1,2,3,4,5],method:'post',
                //mocky:'http://www.mocky.io/v2/5cfcbf4b3200005900ccd40a',
                url:'/outpatientCharge/charge'
            },//退费
            withdraw:{
                allow:[1,2,3,4,5],method:'post',
                //mocky:'http://www.mocky.io/v2/5cfcbf4b3200005900ccd40a',
                url:'/outpatientCharge/withdraw'
            },//通过病历号, 获取收费项目列表（已缴费）,
            getChargedItems:{
                allow:[1,2,3,4,5],method:'post',
                //mocky:'http://www.mocky.io/v2/5cfe6431320000660045f07a',
                url:'/outpatientCharge/getHistoryChargeItems'
            }
        },
        //门诊日结
        dailyCollect:{
            //初始化面板
            getHistoryList:{
                allow:[1,2,3,4,5],method:'get',
                //mocky:'http://www.mocky.io/v2/5cfe7ce9320000660045f137',
                url:'/dailyCollect/list'
            },
            //日结详细信息
            dailyCollectDetail:{
                allow:[1,2,3,4,5],method:'post',
                //mocky:'http://www.mocky.io/v2/5cfe5bfc3200004f0045f047',
                url:'/dailyCollect/detail'
            },
            //日结请求
            dailyCollectRequest:{
                allow:[1,2,3,4,5],method:'post',
                //mocky:'http://www.mocky.io/v2/5cfe5c3c320000660045f04b',
                url:'/dailyCollect/collect'
            }
        },
    },
    //门诊医生
    outpatientDoctor:{
        //门诊病历管理
        medicalRecord:{
            //获取全部中医诊断和西医诊断
            allDiagnoseDiseases:{
                url:'/medicalRecord/allDiagnoseDiseases',
                allow:[1,2,3,4,5],method:'post'
            },
            //同步病人列表
            getPatientList:{
                //mocky:'http://www.mocky.io/v2/5d008c143200005f00f9d582',
                url:'/medicalRecord/getPatientList',
                allow:[1,2,3,4,5],method:'post'
            },
            //根据ID查询一个病人的挂号信息,似乎是药品取药那里用到 !!!!
            //registrationInfo:{
            //    //mocky:'http://www.mocky.io/v2/5cffbab83200006100eac96a',
            //    url:'/medicalRecord/medicalRecordHistory',
            //    allow:[1,2,3,4,5],method:'post'
            //},
            //历史病历 
            historyMedicalRecordList:{
                allow:[1,2,3,4,5],method:'post',
                //mocky:'http://www.mocky.io/v2/5d03185330000051001f4a44',
                url:'/medicalRecord/allHistoryMedicalRecord'
            },//获取（创建）病历
            get:{
                allow:[1,2,3,4,5],method:'post',
                //mocky:'http://www.mocky.io/v2/5d0ccd313500006200b89b05',
                url:'/medicalRecord/getMedicalRecord'
            },//更新(暂存)病历
            update:{
                allow:[1,2,3,4,5],method:'post',
                //mocky:'http://www.mocky.io/v2/5cfcbf4b3200005900ccd40a',
                url:'/medicalRecord/updateMedicalRecord'
            },//保存病历
            save:{
                allow:[1,2,3,4,5],method:'post',
                //mocky:'http://www.mocky.io/v2/5cfcbf4b3200005900ccd40a',
                url:'/medicalRecord/saveMedicalRecord'
            },//确诊提交病历
            confirm:{
                allow:[1,2,3,4,5],method:'post',
                //mocky:'http://www.mocky.io/v2/5cfcbf4b3200005900ccd40a',
                url:'/medicalRecord/confirmMedicalRecord'
            }
        },
        //病历模板
        medicalRecordTemplate:{
            //获取全部病历模板
            list:{
                //mocky:'http://www.mocky.io/v2/5d030d5c30000055001f4a1f',
                url:'/medicalRecordTemplate/list',
                allow:[1,2,3,4,5],method:'post'
            },//获得模板详细
            detail:{
                //mocky:'http://www.mocky.io/v2/5d030bc630000060001f4a1e',
                url:'/medicalRecordTemplate/detail',
                allow:[1,2,3,4,5],method:'post'
            },//创建模板
            create:{
                //mocky:'http://www.mocky.io/v2/5cfe5c3c320000660045f04b',
                url:'/medicalRecordTemplate/create',
                allow:[1,2,3,4,5],method:'post'
            },//更新模板
            update:{
                //mocky:'http://www.mocky.io/v2/5cfe5c3c320000660045f04b',
                url:'/medicalRecordTemplate/update',
                allow:[1,2,3,4,5],method:'post'
            },//删除模板
            delete:{
                //mocky:'http://www.mocky.io/v2/5cfe5c3c320000660045f04b',
                url:'/medicalRecordTemplate/delete',
                allow:[1,2,3,4,5],method:'post'
            }
        },
        //诊断模板
        diagnoseTemplate:{
            //获取全部诊断模板
            list:{
                //mocky:'http://www.mocky.io/v2/5d05088d3200004b00d78c1b',
                url:'/diagnoseTemplate/list',
                allow:[1,2,3,4,5],method:'post'
            },//获得模板详细
            detail:{
                //mocky:'http://www.mocky.io/v2/5d0506743200001400d78c18',
                url:'/diagnoseTemplate/detail',
                allow:[1,2,3,4,5],method:'post'
            },//创建模板
            create:{
                //mocky:'http://www.mocky.io/v2/5cfe5c3c320000660045f04b',
                url:'/diagnoseTemplate/create',
                allow:[1,2,3,4,5],method:'post'
            },//更新模板
            update:{
                //mocky:'http://www.mocky.io/v2/5cfe5c3c320000660045f04b',
                url:'/diagnoseTemplate/update',
                allow:[1,2,3,4,5],method:'post'
            },//删除模板
            delete:{
                //mocky:'http://www.mocky.io/v2/5cfe5c3c320000660045f04b',
                url:'/diagnoseTemplate/delete',
                allow:[1,2,3,4,5],method:'post'
            }
        },
        //检查 检验 处置
        IAD:{
            //根据类型获取所有的可选项目
            allItems:{
                //mocky:' http://www.mocky.io/v2/5d078bcb30000086000521a5',
                url:'/exam/allItems',
                allow:[1,2,3,4,5],method:'post'
            },//创建
            create:{
                //mocky:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',
                url:'/exam/create',
                allow:[1,2,3,4,5],method:'post'
            },//删除
            delete:{
                //mocky:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',
                url:'/exam/delete',
                allow:[1,2,3,4,5],method:'post'
            },//更新
            update:{
                //mocky:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',
                url:'/exam/update',
                allow:[1,2,3,4,5],method:'post'
            },//发送
            send:{
                //mocky:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',
                url:'/exam/send',
                allow:[1,2,3,4,5],method:'post'
            },
            //作废
            cancel:{
                //mocky:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',
				url: '/exam/cancel',
                allow:[1,2,3,4,5],method:'post'
            },//获取当前开具的列表
            list:{
                //mocky:'http://www.mocky.io/v2/5d0cd0013500005b00b89b18',
				url: '/exam/list',
                allow:[1,2,3,4,5],method:'post'
            }
        },
        //检查 检验 处置的组套（模板）
        IADTemplate:{
            //创建
            create:{
                //mocky:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',
				url: '/examTemplate/create',
                allow:[1,2,3,4,5],method:'post'
            },//删除
            delete:{
                //mocky:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',
				url: '/examTemplate/delete',
                allow:[1,2,3,4,5],method:'post'
            },//更新
            update:{
                //mocky:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',
				url: '/examTemplate/update',
                allow:[1,2,3,4,5],method:'post'
            },//获取当前全部组套
            all:{
                //mocky:'http://www.mocky.io/v2/5d0795f13400000e005d938a',
				url: '/examTemplate/all',
                allow:[1,2,3,4,5],method:'post'
            },//获取详细信息
            detail:{
                //mocky:'http://www.mocky.io/v2/5d0885a73400004d005d978f',
				url: '/examTemplate/detail',
                allow:[1,2,3,4,5],method:'post'
            }
        },
        //处方(成药 草药 医技补录)
        prescription:{
            //获取所有的药品
            allDrugs:{
                //mocky:'http://www.mocky.io/v2/5d089c6d34000059005d989d',
				url: '/prescription/allDrugs',
                allow:[1,2,3,4,5],method:'post',
            },//获取全部的处方
            allPrescription:{
                //mocky:'http://www.mocky.io/v2/5d08f5a43400000e00d82cb6',
				url: '/prescription/allPrescription',
                allow:[1,2,3,4,5],method:'post'
            },//创建处方
            create:{
                //mocky:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',
				url: '/prescription/create',
                allow:[1,2,3,4,5],method:'post'
            },//暂存（更新）处方
            update:{
                //mocky:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',
				url: '/prescription/update',
                allow:[1,2,3,4,5],method:'post'
            },//发送处方
            send:{
                //mocky:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',
				url: '/prescription/submit',
                allow:[1,2,3,4,5],method:'post'
            },//删除处方
            delete:{
                //mocky:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',
				url: '/prescription/delete',
                allow:[1,2,3,4,5],method:'post'
            },//作废处方
            cancel:{
                //mocky:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',
				url: '/prescription/cancel',
                allow:[1,2,3,4,5],method:'post'
            }
        },
        prescriptionTemplate:{
            //获取全部的组套
            list:{
                //mocky:'http://www.mocky.io/v2/5d08b2e434000064985d996e',
				url: '/prescriptionTemplate/list',
                allow:[1,2,3,4,5],method:'post'
            },//创建组套
            create:{
                //mocky:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',
				url: '/prescriptionTemplate/create',
                allow:[1,2,3,4,5],method:'post'
            },//组套详情
            detail:{
                //mocky:'http://www.mocky.io/v2/5d0b481e2f00007300e3ef49',
				url: '/prescriptionTemplate/detail',
                allow:[1,2,3,4,5],method:'post'
            },//删除组套
            delete:{
                //mocky:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',
				url: '/prescriptionTemplate/delete',
                allow:[1,2,3,4,5],method:'post'
            },//更新组套
            update:{
                //mocky:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',
				url: '/prescriptionTemplate/update',
                allow:[1,2,3,4,5],method:'post'
            }
        },
        //患者费用查询
        patientFee:{
            //获取全部费用
            historyChargeItems:{
                //mocky:'http://www.mocky.io/v2/5d099fe53400005e29d82f11',
				url: '/outpatientCharge/historyChargeItems',
                allow:[1,2,3,4,5],method:'post'
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
				url: '/examExcute/searchRegistration',
                allow:[1,2,3,4,5],method:'post'
            },//获取可执行项目列表（包含未缴费）
            allExcuteProject:{
                //mocky:'http://www.mocky.io/v2/5d0b32e82f00007000e3ee82',
				url: '/examExcute/allExam',
                allow:[1,2,3,4,5],method:'post'
            },//登记
            register:{
                //mocky:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',
				url: '/examExcute/register',
                allow:[1,2,3,4,5],method:'post'
            },//录入结果
            submitResult:{
                //mocky:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',
				url: '/examExcute/submitResult',
                allow:[1,2,3,4,5],method:'post'
            },//获取结果
            getResult:{
                //mocky:'http://www.mocky.io/v2/5d0cd6ec3500000d00b89b37',
				url: '/examExcute/getResult',
                allow:[1,2,3,4,5],method:'post'
            }
        }
    },
    //药房
    pharmacyWorkStation:{
        //药品目录管理
        drugInfoManagement:{
            //增加药品信息
            add:{
                allow:[1,2,3,4,5],method:'post',
                //mocky: 'http://www.mocky.io/v2/5cff0e4b3200004d0045f2d5',
				url: '/drugInfoManagement/add'
            },//删除药品信息
            delete:{
                allow:[1,2,3,4,5],method:'post',
                //mocky:'http://www.mocky.io/v2/5cff0e4b3200004d0045f2d5',
				url: '/drugInfoManagement/delete'
            },//更新药品信息
            update:{
                allow:[1,2,3,4,5],method:'post',
                //mocky:'http://www.mocky.io/v2/5cff0e4b3200004d0045f2d5',
				url: '/drugInfoManagement/update'
            },//全部药品信息
            all:{
                allow:[1,2,3,4,5],method:'post',
                //mocky:'http://www.mocky.io/v2/5cff0f0a3200004e0045f2d9',
				url: '/drugInfoManagement/all'
            },//根据药品名字模糊搜索药品
            getDrugInfoByName:{
                allow:[1,2,3,4,5],method:'post',
                url:'/drugInfoManagement/getDrugInfoByName'
            }
        },
        //收发药
        drugDispatcher:{
            //可发药表
            dispenseList:{
                allow:[1,2,3,4,5],method:'post',
                url:'/drugDispense/list',
                //mocky:'http://www.mocky.io/v2/5d021de03100003400ab2c85'
            },//可退药表
            withdrawableList:{
                allow:[1,2,3,4,5],method:'post',
                //mocky:'http://www.mocky.io/v2/5d021de03100003400ab2c85',
				url: '/drugWithdrawal/list'
            },//已经退药表
            withdrawedList:{
                allow:[1,2,3,4,5],method:'post',
                //mocky:'http://www.mocky.io/v2/5d021de03100003400ab2c85',
				url: '/drugDispense/withdrawHistory'
            },//发药
            dispenseSubmit:{
                allow:[1,2,3,4,5],method:'post',
                //mocky:'http://www.mocky.io/v2/5cfcbf4b3200005900ccd40a',
				url: '/drugDispense/submit'
            },//退药
            withdrawSubmit:{
                allow:[1,2,3,4,5],method:'post',
                //mocky:'http://www.mocky.io/v2/5cfcbf4b3200005900ccd40a',
				url: '/drugWithdrawal/submit'
            }
        }
    },
    //财务处统计
    financialAdmin:{
        //收费项目管理
        expenseClassification:{
            all:{//获得全部的收费项目
                allow:[1,2,3,4,5],method:'get', 
                //mocky:'http://www.mocky.io/v2/5d09dc833400001229d8303f',
				url: '/expenseClassificationManage/all'
            },
            add:{//添加收费项目
                allow:[1,2,3,4,5],method:'post',
                //mocky:'http://www.mocky.io/v2/5d09dca13400005e29d83040',
				url: '/expenseClassificationManage/add'
            },
            delete:{//删除收费项目
                allow:[1,2,3,4,5],method:'post',
                //mocky:'http://www.mocky.io/v2/5d09dca13400005e29d83040',
				url: '/expenseClassificationManage/delete'
            },
            update:{//更新收费项目
                allow:[1,2,3,4,5],method:'post',
                //mocky:'http://www.mocky.io/v2/5d09dca13400005e29d83040',
				url: '/expenseClassificationManage/update'
            }
        },
        //日结核对
        dailyReportCheck:{
            //初始化，加载基本数据(收费员名单)
            init:{
                allow:[1,2,3,4,5],method:'post',
                //mocky:'http://www.mocky.io/v2/5d0c37233500004a00b896f6',
				url: '/outpatientDailyReportCheck/init',
            },//获取报告
            getReport:{
                allow:[1,2,3,4,5],method:'post',
                //mocky:'http://www.mocky.io/v2/5d0c37d93500004a00b896f9',
				url: '/outpatientDailyReportCheck/getReport'
            },//财务入库
            confirmCheck:{
                allow:[1,2,3,4,5],method:'post',
                //mocky:'http://www.mocky.io/v2/5d0c38b23500002d00b896fe',
				url: '/outpatientDailyReportCheck/confirmCheck'
            },//历史日结
            history:{
                allow:[1,2,3,4,5],method:'post',
                //mocky:'http://www.mocky.io/v2/5d0c394e3500005100b89700',
				url: '/outpatientDailyReportCheck/history'
            }
        },
        workloadStatistic:{
            //部门工作量统计
            department:{
                allow:[1,2,3,4,5],method:'post',
                //mocky:'http://www.mocky.io/v2/5d0c42803500002d00b8971c',
				url: '/workloadStatistic/departmentStatistic'
            },
            //个人工作量统计
            personal:{
                allow:[1,2,3,4,5],method:'post',
				url: '/workloadStatistic/userStatistic'
            },
            //费用类型工作量统计
            typeStatistic:{
                allow:[1,2,3,4,5],method:'post',
				url: '/workloadStatistic/typeStatistic'
            },
            //医生统计(新增)
            outpatientDoctor:{
                allow:[1,2,3,4,5],method:"post",
                url:'/workloadStatistic/outpatientDoctor'
            },
            //医技医生统计(新增)
            doctorOfTechnology:{
                allow:[1,2,3,4,5],method:"post",
                url:'/workloadStatistic/doctorOfTechnology'
            }
        }
    }
}

export default API;

