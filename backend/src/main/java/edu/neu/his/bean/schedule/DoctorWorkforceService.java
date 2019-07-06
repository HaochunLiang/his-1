package edu.neu.his.bean.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class DoctorWorkforceService {
    private Date current = new Date();
    private LocalDate currentLocalDate = LocalDate.now();
    private int numberPerDay = 3;
    private float days = 2;
    //排班表格中每一项的id
    private int[][] schedule;
    //剩余号数
    private int[][] needToSche;
    //号别
    private String[][] registration_Level;
    //有效状态
    private boolean[][] valids;
    //限号数量
    //private int[][] reg_limits;
    //id
    //private int[][] ids;
    private int index = 0;
    //存放排班时间中的i，j
    private int[][] put;
    //排班时间链表
    private ArrayList<int[][]> puts;
    private Date startDate = new Date();
    private Date printDate;

    //每个人的已知＋计算的排班信息
    ArrayList<DoctorWorkforce> doctorWorkforces = new ArrayList<DoctorWorkforce>();
    ArrayList<int[][]> emptys = new ArrayList<int[][]>();
    //用于存入数据库＋显示在网站上的排班信息
    List<Schedule> getSchedules = new ArrayList<Schedule>();
    //List<Schedule> getSchedulesA = new ArrayList<Schedule>();
    //List<Schedule> getSchedulesB = new ArrayList<Schedule>();
    int[][] empty;


    int totalLimits = 0;
    int totalLimitsA = 0;
    int totalLimitsB = 0;
    int aveNumOfRegister = 10;

    int posi = 0;
    SimpleDateFormat formatter = new SimpleDateFormat(("yyyy-MM-dd"));
    ParsePosition pos = new ParsePosition(0);


    @Autowired
    DoctorWorkforceMapper doctorWorkforceMapper;

    @Transactional
    public void setNumberPerDay(Integer numberPerDay){
        this.numberPerDay = numberPerDay;
        System.out.println("numberPerDay:"+numberPerDay);
        //排班表格中每一项的name
        schedule = new int[(int) days][numberPerDay];
        //剩余号数
        needToSche = new int[(int) days][numberPerDay];
        //号别
        registration_Level = new String[(int) days][numberPerDay];
        //有效状态
        valids = new boolean[(int) days][numberPerDay];
        //限号数量
        //reg_limits = new int[(int) days][numberPerDay];
        //id
        //ids = new int[(int) days][numberPerDay];
    }

    @Transactional
    public boolean addDoctorWorkforce(Schedule schedule) {
        if (schedule.getName() != null) {
            //int uid = doctorWorkforceMapper.name2ID(schedule.getName());
            int uid = schedule.getId();
            int registration_level_id = doctorWorkforceMapper.registrationLevel2ID(schedule.getRegistration_Level());
            doctorWorkforceMapper.addDoctorWorkforce(schedule, uid, registration_level_id);


        } else {
            System.out.println("Name is null");
        }
        return true;
    }


    //向排班结果中添加一行
    @Transactional
    public void addOneDoctorWorkforce(Schedule schedule) {
        int registration_level_id = doctorWorkforceMapper.registrationLevel2ID(schedule.getRegistration_Level());
        //int uid = doctorWorkforceMapper.name2ID(schedule.getName());
        int uid = schedule.getId();
        doctorWorkforceMapper.addOneDoctorWorkforce(schedule,registration_level_id,uid);
    }

    @Transactional
    public List<DoctorSchedulingInfo> getName(String name){
        return doctorWorkforceMapper.getName(name);
    }

    //删除排班结果中的行，如果早于当前时间则不可删
    @Transactional
    public void deleteDoctorWorkforceById(int id,String scheduleDate) {
        LocalDate beginDateTime = LocalDate.parse(scheduleDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if(beginDateTime.compareTo(LocalDate.now())>0) {
            doctorWorkforceMapper.deleteDoctorWorkforceById(id);
        }
    }


    @Transactional
    public void update(DoctorWorkforce1 doctorWorkforce1) {
        doctorWorkforceMapper.updateDoctorWorkforce(doctorWorkforce1);
    }

    @Transactional
    public List<DoctorWorkforce1> findAll() {
        return doctorWorkforceMapper.findAll();
    }

    @Transactional
    public List<AllSchedule> findAddInfo(String name) {
        return doctorWorkforceMapper.findAddInfo(name);
    }

    @Transactional
    public List<AllSchedule> findAddInfoByID(int id) {
        return doctorWorkforceMapper.findAddInfoByID(id);
    }

    @Transactional
    public List<AllSchedule> findAllSchedule() {
        return doctorWorkforceMapper.findAllSchedule();
    }

    //新增行时查找行冲突
    @Transactional
    public List<AllSchedule> findAddRowConflict(int uid,String schedule_date,String shift) {
        //int uid = doctorWorkforceMapper.name2ID(name);
        return doctorWorkforceMapper.findAddRowConflict(uid,schedule_date,shift);
    }

    //查找时间冲突
    @Transactional
    public List<AllSchedule> findTimeConflict(List<String> schedule_date) {
//        System.out.println("server-timeConflict:" + schedule_date);
        List<AllSchedule> currTimes = new ArrayList<AllSchedule>();
        List<AllSchedule> temp = new ArrayList<AllSchedule>();

        //使用localdate计算时间
        String dateStr1 = schedule_date.get(0);
        pos = new ParsePosition(0);
        Date date1 = formatter.parse(dateStr1, pos);
        //startDate = date1;
        String dateStr2 = schedule_date.get(1);
        pos = new ParsePosition(0);
        Date date2 = formatter.parse(dateStr2, pos);


        for(LocalDate localDate = LocalDate.parse(dateStr1, DateTimeFormatter.ofPattern("yyyy-MM-dd"));!localDate.toString().equals(dateStr2);localDate = localDate.minusDays(-1)) {
//            System.out.println("day+1:" + localDate.toString());
            temp = doctorWorkforceMapper.findTimeConflict(localDate.toString());
//            System.out.println("TEMP:" + temp);

            if (!temp.isEmpty()) {
                for (AllSchedule t : temp) {
                    currTimes.add(t);
                }
            }
        }

        if (!temp.isEmpty()) {
            for (AllSchedule t : doctorWorkforceMapper.findTimeConflict(formatter.format(date2))) {
                currTimes.add(t);
            }
        }
//        System.out.println("return currtimes");
        return currTimes;
    }

    //输入选择的排班人员的已知信息
    @Transactional
    public void chooseDoctorWorkforceByName(int id,String name, String shift, String expiry_date, Integer limit,String registration_Level) {
        pos = new ParsePosition(0);
        DoctorWorkforce doctorWorkforce = new DoctorWorkforce(id,name, shift, formatter.parse(expiry_date, pos), limit,registration_Level);
        doctorWorkforces.add(doctorWorkforce);
    }

    //根据排班限额计算比例
    public void calLimitRatio() {
        /*   for (DoctorWorkforce doctorWorkforce : doctorWorkforces) {
            doctorWorkforce.setValid((expiry_date2valid(doctorWorkforce.getExpiry_date())));
            System.out.println("d "+doctorWorkforce.getName()+" set ratio:"+Math.round((days * numberPerDay / (totalLimits))));
            doctorWorkforce.setLimitRatio(Math.round((days * numberPerDay / (totalLimits))));
            doctorWorkforce.setScheduled(doctorWorkforce.getLimitRatio());
            System.out.println(doctorWorkforce.getName() + " scheduled=" + doctorWorkforce.getScheduled());
            if(doctorWorkforce.getValid()==false){
                doctorWorkforces.remove(doctorWorkforce);
            }
        }*/

        Iterator<DoctorWorkforce> it = doctorWorkforces.iterator();
        while(it.hasNext()){
            DoctorWorkforce doctorWorkforce = it.next();
            doctorWorkforce.setValid((expiry_date2valid(doctorWorkforce.getExpiry_date())));
            System.out.println("d "+doctorWorkforce.getName()+" set ratio:"+Math.round((days * numberPerDay / (totalLimits))));
            doctorWorkforce.setLimitRatio(Math.round((days * numberPerDay / (totalLimits))));
            doctorWorkforce.setScheduled(doctorWorkforce.getLimitRatio());
            System.out.println(doctorWorkforce.getName() + " scheduled=" + doctorWorkforce.getScheduled()+" valid="+doctorWorkforce.getValid());

            if(doctorWorkforce.getValid()==false){
                System.out.println("remove:"+doctorWorkforce.getName());
                it.remove();
            }
        }
    }

    //获取全部挂号级别
    @Transactional
    public List<String> getRegistrationLevels(){
        return doctorWorkforceMapper.getRegistrationLevels();
    }

    //按照挂号级别排班
    @Transactional
    public void scheduleByRegistrationLevel(List<String> dateRange,List<String> registrationLevels) throws ParseException {
        ArrayList<DoctorWorkforce> doctorWorkforceInput = new ArrayList<DoctorWorkforce>();
        ArrayList<DoctorWorkforce> doctorWorkforcesTemp = new ArrayList<DoctorWorkforce>();
        ArrayList<Schedule> doctorWorkforcesLevel = new ArrayList<Schedule>();
        List<Schedule> getSchedulesTemp = new ArrayList<Schedule>();
        int num = 0;
        doctorWorkforceInput = doctorWorkforces;
        for(String reg:registrationLevels){
            System.out.println("reg:"+reg);

            //num = nums.get(registrationLevels.indexOf(reg));
            //if(num!=0){
            //    setNumberPerDay(num);
            //}
            //setNumberPerDay(nums.get(registrationLevels.indexOf(reg)));

            for(DoctorWorkforce doctorWorkforce : doctorWorkforceInput) {
                System.out.println("name:"+doctorWorkforce.getName()+" doctorWorkforce.getRegistration_Level():"+doctorWorkforce.getRegistration_Level());
                if(doctorWorkforce.getRegistration_Level().equals(reg)){
                    doctorWorkforcesTemp.add(doctorWorkforce);
                    totalLimits++;
                    System.out.println("totalLimits:"+totalLimits+" "+doctorWorkforce.getName());
                }
            }
            doctorWorkforces = doctorWorkforcesTemp;
            schedule(dateRange);
            getSchedulesTemp = getSchedules;
            for(Schedule s:getSchedulesTemp){
                doctorWorkforcesLevel.add(s);
            }
            doctorWorkforcesTemp = new ArrayList<DoctorWorkforce>();

        }
        getSchedules = doctorWorkforcesLevel;
    }

 /*   @Transactional
    public void scheduleByRegistrationLevel(List<String> dateRange) throws ParseException {
        ArrayList<DoctorWorkforce> doctorWorkforcesA = new ArrayList<DoctorWorkforce>();
        ArrayList<DoctorWorkforce> doctorWorkforcesB = new ArrayList<DoctorWorkforce>();
        for (DoctorWorkforce doctorWorkforce : doctorWorkforces) {
            if(doctorWorkforce.getRegistration_Level().equals("专家号")){
                doctorWorkforcesA.add(doctorWorkforce);
                totalLimitsA ++;
                System.out.println("totalLimits:"+totalLimits);
            }else if(doctorWorkforce.getRegistration_Level().equals("普通号")){
                doctorWorkforcesB.add(doctorWorkforce);
                totalLimitsB ++;
                System.out.println("totalLimits:"+totalLimits);
            }else{
                System.out.println("不是专家号，也不是普通号");
            }
        }
        doctorWorkforces = doctorWorkforcesA;
        totalLimits = totalLimitsA;
        schedule(dateRange);
        getSchedulesA = getSchedules;

        doctorWorkforces = doctorWorkforcesB;
        totalLimits = totalLimitsA;
        schedule(dateRange);
        getSchedulesB = getSchedules;
        for(Schedule s:getSchedulesA){
            getSchedules.add(s);
        }
    }*/

    @Transactional
    public void schedule(List<String> dateRange) throws ParseException {
        days = calcuDays(dateRange);
        System.out.println("days=" + days);

        schedule = new int[(int) days][numberPerDay];
        needToSche = new int[(int) days][numberPerDay];
        registration_Level = new String[(int)days][numberPerDay];
        valids = new boolean[(int) days][numberPerDay];
        //reg_limits = new int[(int) days][numberPerDay];
        //ids = new int[(int) days][numberPerDay];

        calLimitRatio();
        for (DoctorWorkforce doctorWorkforce : doctorWorkforces) {
            int i = 0, j = 0;
            if (i % 2 == 1)
                i++;
            if (doctorWorkforce.getShift().equals("上午")) {
                setSchedule1(i, j, doctorWorkforce, false);
            }
        }
//下午
        for (DoctorWorkforce doctorWorkforce : doctorWorkforces) {
            int i = 0, j = 0;
            if (i % 2 == 0)
                i++;
            if (doctorWorkforce.getShift().equals("下午")) {
                setSchedule1(i, j, doctorWorkforce, false);
            }
        }

//全天

        for (DoctorWorkforce doctorWorkforce : doctorWorkforces) {
            if (doctorWorkforce.getShift().equals("全天")) {
                for (int i = schedule.length - 1; i >= 0; i--) {
                    for (int j = schedule[0].length - 1; j >= 0; j--) {
                        if (doctorWorkforce.getScheduled() > 0 && schedule[i][j] == 0) {
                            schedule[i][j] = doctorWorkforce.getId();
                            needToSche[i][j] = doctorWorkforce.getScheduled();
                            registration_Level[i][j] = doctorWorkforce.getRegistration_Level();
                            valids[i][j] = doctorWorkforce.getValid();
                            //reg_limits[i][j] = doctorWorkforce.getLimit();
                            //ids[i][j] = doctorWorkforce.getId();
                            doctorWorkforce.setScheduled(doctorWorkforce.getScheduled() - 1);

                            put = new int[1][2];
                            put[0][0] = i;
                            put[0][1] = j;
                            puts = doctorWorkforce.getWorkforce();
                            if (puts == null) {
                                puts = new ArrayList<int[][]>();
                            }
                            puts.add(put);
                            doctorWorkforce.setWorkforce(puts);

                            break;
                        }
                    }
                    if (doctorWorkforce.getScheduled() <= 0) {
                        break;
                    }
                }
            }
        }

        System.out.println();
        System.out.println("初始排班:");
        for (int i = 0; i < schedule.length; i++) {
            for (int j = 0; j < schedule[0].length; j++) {
                System.out.print(schedule[i][j] + "   ");
                if (schedule[i][j] == 0) {
                    empty = new int[1][2];
                    empty[0][0] = i;
                    empty[0][1] = j;
                    emptys.add(empty);
                }
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();


        for (DoctorWorkforce doctorWorkforce : doctorWorkforces) {
            System.out.println("schedule=" + doctorWorkforce.getScheduled());
        }

        //当出现医生的scheduled未排满时，交换位置
        boolean R;
        int[][] temp = new int[1][2];
        int indexE = 0;
        int indexRemove = -1;
        boolean R1;
        boolean next;
        ArrayList<int[][]> adds;
        int[][] add;
        //找到scheduled!=0的医生a
        for (DoctorWorkforce doctorWorkforce : doctorWorkforces) {
            if (doctorWorkforce.getScheduled() != 0) {
                //寻找交换的医生b
                for (DoctorWorkforce doctorWorkforceR : doctorWorkforces) {
                    next = false;
                    if (doctorWorkforceR.getShift().equals("全天")) {
                        R = true;
                        int i = 0;
                        indexE = 0;
                        indexRemove = -1;
                        //找到空位置
                        for (int[][] e : emptys) {
                            i = e[0][0];
                            //排除医生b在空位置行已排班的情况
                            for (int j = 0; j < schedule[0].length; j++) {
                                if (schedule[e[0][0]][j] != 0 && schedule[e[0][0]][j]==doctorWorkforceR.getId()) {
                                    R = false;
                                    break;
                                }
                            }
                            if (R == true) {
                                schedule[e[0][0]][e[0][1]] = doctorWorkforceR.getId();
                                needToSche[e[0][0]][e[0][1]] = doctorWorkforceR.getScheduled();
                                registration_Level[e[0][0]][e[0][1]] = doctorWorkforceR.getRegistration_Level();
                                valids[e[0][0]][e[0][1]] = doctorWorkforceR.getValid();

                                adds = doctorWorkforceR.getWorkforce();
                                add = new int[1][2];
                                add[0][0] = e[0][0];
                                add[0][1] = e[0][1];
                                adds.add(add);

                                puts = doctorWorkforceR.getWorkforce();

                                //排除医生a在与医生b的交换行已经排班的情况
                                for (int[][] p : puts) {
                                    //int[][] p = puts.get(0);
                                    R1 = true;
                                    for (int s = 0; s < schedule[0].length; s++) {
                                        if (schedule[p[0][0]][s]==doctorWorkforce.getId()) {
                                            R1 = false;
                                        }
                                    }
                                    if (R1) {
                                        schedule[p[0][0]][p[0][1]] = doctorWorkforce.getId();
                                        needToSche[p[0][0]][p[0][1]] = doctorWorkforce.getScheduled();
                                        registration_Level[p[0][0]][p[0][1]] = doctorWorkforce.getRegistration_Level();
                                        valids[p[0][0]][p[0][1]] = doctorWorkforce.getValid();

                                        adds = doctorWorkforce.getWorkforce();
                                        add = new int[1][2];
                                        add[0][0] = p[0][0];
                                        add[0][1] = p[0][1];
                                        adds.add(add);

                                        next = true;
                                        break;
                                    }
                                }
                                indexRemove = indexE;
                            }
                            indexE++;
                            if (next) {
                                break;
                            }
                        }
                        if (indexRemove != -1) {
                            emptys.remove(indexRemove);
                        }
                    }
                }
            }

        }

        String valid = "";
        System.out.println();
        System.out.println("优化排班:");
        getSchedules = new ArrayList<Schedule>();
        String currentShift;
        for (int i = 0; i < schedule.length; i++) {
            for (int j = 0; j < schedule[0].length; j++) {
                System.out.print(schedule[i][j] + "   ");
                //Schedule s = new Schedule(schedule[i][j],i,j);
                if (schedule[i][j] != 0) {
                    if (i % 2 == 0) {
                        currentShift = "上午";
                    } else {
                        currentShift = "下午";
                    }
                    if (valids[i][j] == true) {
                        valid = "有效";
                    } else if (valids[i][j] == false) {
                        valid = "无效";
                    } else {
                        System.out.println("valid is null");
                    }
                    getSchedules.add(new Schedule(schedule[i][j], addDates(i, j), currentShift, date2Week(addDates(i, j)), valid,registration_Level[i][j]));

                }
            }
            System.out.println();
        }
    }

    //查找排班限额
    public int findLimit(ArrayList<DoctorWorkforce> doctorWorkforces, String name) {
        int limit = 0;
        for (DoctorWorkforce doctorWorkforce : doctorWorkforces) {
            if (doctorWorkforce.getName().equals(name)) {
                limit = doctorWorkforce.getLimit();
                doctorWorkforce.setLimit(limit - 1);
            }
        }
        return limit;
    }

    //计算是否有效
    public Boolean expiry_date2valid(Date date) {
        return  UDate2LocalDate(date).compareTo(currentLocalDate)>0;
    }

    //计算选择范围内的天数
    public int calcuDays(List<String> dateRange) {
        String dateStr1 = dateRange.get(0);
        String dateStr2 = dateRange.get(1);
        pos = new ParsePosition(0);
        Date date1 = formatter.parse(dateStr1, pos);
        startDate = LocalDateToUdate(LocalDate.parse(dateStr1, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        return (int)(LocalDate.parse(dateStr1, DateTimeFormatter.ofPattern("yyyy-MM-dd")).until(LocalDate.parse(dateStr2, DateTimeFormatter.ofPattern("yyyy-MM-dd")), ChronoUnit.DAYS)+1)*2;

    }

    //返回排班时期
    public String addDates(int i, int j) throws ParseException {
        int days = i / 2;
        String startStr = formatter.format(startDate);

        String currStr = UDate2LocalDate(startDate).minusDays(-1*days).toString();
        //System.out.println("Start Day1:"+startDate);
        //System.out.println("currStr:"+currStr);
        return currStr;
    }

    //日期转星期
    public int date2Week(String date) {
        DateFormat df = DateFormat.getDateInstance();
        try {
            df.parse(date);
            Calendar c = df.getCalendar();
            int day = c.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
            return day;
        } catch (ParseException e) {
            return -1;
        }
    }

    public int setSchedule1(int i, int j, DoctorWorkforce doctorWorkforce, boolean flag) {
        if (doctorWorkforce.getScheduled() == 0 || i >= (int) days || flag) {
            flag = true;
            System.out.println("return -1");
            return -1;
        }
        //当前位置为空->放入
        if (schedule[i][j] == 0) {
            schedule[i][j] = doctorWorkforce.getId();
            needToSche[i][j] = doctorWorkforce.getScheduled();
            registration_Level[i][j] = doctorWorkforce.getRegistration_Level();
            valids[i][j] = doctorWorkforce.getValid();
            doctorWorkforce.setScheduled(doctorWorkforce.getScheduled() - 1);

            //将排班位置存入医生对象
            put = new int[1][2];
            put[0][0] = i;
            put[0][1] = j;
            puts = doctorWorkforce.getWorkforce();
            if (puts == null) {
                puts = new ArrayList<int[][]>();
            }
            puts.add(put);
            for (int[][] p : puts) {
            }
            doctorWorkforce.setWorkforce(puts);

            i += 2;
            return setSchedule1(i, 0, doctorWorkforce, flag);
        } else {
            //该日期未排满－>放入该日期的下一个位置
            if (j < numberPerDay - 1) {
                return setSchedule1(i, ++j, doctorWorkforce, flag);
                //该日期已排满->下一天
            } else {
                i += 2;
                return setSchedule1(i, 0, doctorWorkforce, flag);
            }
        }
    }

    @Transactional
    public List<Schedule> getDoctorWorkforces() {
        doctorWorkforces = new ArrayList<DoctorWorkforce>();
        totalLimits = 0;
        return getSchedules;
    }

    @Transactional
    public Schedule injectDoctoeWorkforce(Schedule schedule){
        return (doctorWorkforceMapper.injectDoctorWorkforce(schedule)).get(0);
    }

    //Date类型转LocalDate
    public LocalDate UDate2LocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime.toLocalDate();
    }

    //LocalDate转Date
    public Date LocalDateToUdate(LocalDate localDate) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        return Date.from(instant);
    }


}
