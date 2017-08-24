package com.xmjj.rxretrofit_master.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by Somewereb on 2017/8/18.
 */
@Table(database = AppDataBase.class)
public class DBFlowModel extends BaseModel{

    @PrimaryKey(autoincrement = true)//ID自增
    public long id;

    /**
     * 姓名
     */
    @Column
    public String name;

    /**
     * 年龄
     */
    @Column
    public int age;

    /**
     * 性别
     */
    @Column
    public boolean sex;

    @Column
    public String content;//增加的字段

    @Column
    public int grade;//增加的字段

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public boolean isSex() {
		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}
}
