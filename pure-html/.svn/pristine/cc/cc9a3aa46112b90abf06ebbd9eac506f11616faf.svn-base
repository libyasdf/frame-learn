package com.sinosoft.sinoep.urge.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "sys_urge")
public class SysUrge {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    /**
     * 起草人ID
     */
    @Column(name = "sender_id")
    private String  senderId;

    /**
     * 起草人ID
     */
    @Column(name = "subject")
    private String  subject;

    /**
     * 起草人ID
     */
    @Column(name = "content")
    private String  content;


    /**
     * 起草人ID
     */
    @Column(name = "send_time")
    private String  sendTime;

    /**
     * 起草人ID
     */
    @Column(name = "accepter_id")
    private String  accepterId;

    /**
     * 起草人ID
     */
    @Column(name = "accept_time")
    private String  acceptTime;

    /**
     * 起草人ID
     */
    @Column(name = "time_stamp")
    private String  timeStamp;

    /**
     * 起草人ID
     */
    @Column(name = "module_type")
    private String  moduleType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getAccepterId() {
        return accepterId;
    }

    public void setAccepterId(String accepterId) {
        this.accepterId = accepterId;
    }

    public String getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }
}
