package com.example.mytask.service.archiveEquipmentTime;

import com.example.mytask.service.OpcValueListener;
import com.example.repository.model.PdsEquipproperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class TimeByConditionListener implements OpcValueListener {
    @Autowired
    private ReentrantLock runTimeLock;

    @Autowired
    private List<PdsEquipproperty> timeEps;

    @Autowired
    private List<String> timeEpsIds;

    private Pattern pattern = Pattern.compile("(\\w+)(<|>|<=|>=|!=|=)(\\w+)");

    @Override
    public void init() {
    }

    @Override
    public void update(Map<String, PdsEquipproperty> eps) {
        boolean isLocked = runTimeLock.tryLock();
        if (isLocked) {
            try {
                LocalDateTime now = LocalDateTime.now();
                LocalDateTime endTime1 = now.withHour(23).withMinute(56);
                LocalDateTime endTime2 = now.withHour(23).withMinute(59);
                if (now.isAfter(endTime1) && now.isBefore(endTime2)) {
                    System.out.println("Daily clear time count");
                    for (PdsEquipproperty p : timeEps) {
                        AtomicLong pre = (AtomicLong) p.getValue();
                        if (pre == null) {
                            pre = new AtomicLong(0);
                        }
                        pre.set(0);
                        p.setValue(pre);
                    }
                }

                for (PdsEquipproperty p : timeEps) {
                    Boolean res = parseCondition(p.getProcessvaluecondition(), eps);
                    if (res) {
                        AtomicLong pre = (AtomicLong) p.getValue();
                        if (pre == null) {
                            pre = new AtomicLong(0);
                        }
                        if (pre.get() <= 86400) {
                            pre.getAndAdd(2L);
                            p.setValue(pre);
                        } else {
                            pre.set(0);
                            p.setValue(pre);
                        }
                    }
                }
            } finally {
                runTimeLock.unlock();
            }
        }
    }

    public Boolean parseCondition(String condition, Map<String, PdsEquipproperty> eps) {
        String[] groups = condition.replace(" ", "").split("(\\|\\|)");
        for (String g : groups) {
            Matcher matcher = pattern.matcher(g);
            if (matcher.find() && matcher.group(0) != null && matcher.group(1) != null && matcher.group(
                    2) != null) {
                String left = matcher.group(1);
                String mid = matcher.group(2);
                String right = matcher.group(3);

                Object eps1 = eps.get(left);
                if (eps1 == null) {
                    System.out.println("condition = " + condition + ", eps = " + eps);
                    return false;
                }
                Object lv = eps.get(left).getValue();
//                System.out.println(left + " ," + eps.get(left)
//                        .getTagaddress() + " ," + lv.getClass().getName() + " ," + lv);
                if (lv != null) {
                    if (lv.getClass().getName().equals("java.lang.Boolean")) {
                        lv = lv.equals(true) ? 1 : 0;
                    }
                }
                String lvs = String.valueOf(lv);
                if (mid.equals("=")) {
                    if (lvs.equals(right)) {
                        return true;
                    }
                } else if (mid.equals("!=")) {
                    if (!lvs.equals(right)) {
                        return true;
                    }
                } else if (mid.equals("<")) {
                    if ((Float) lv < Float.valueOf(right)) {
                        return true;
                    }
                } else if (mid.equals(">")) {
                    if ((Float) lv > Float.valueOf(right)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
