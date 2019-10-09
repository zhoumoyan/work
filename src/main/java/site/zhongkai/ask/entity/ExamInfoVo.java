package site.zhongkai.ask.entity;

import java.io.Serializable;

/**
 * 
 * 
 * @author k1732
 * @email zyl52570@163.com
 * @date 2019-01-10 09:29:07
 */
public class ExamInfoVo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String[] examid;

	private String[] a;

	private String[] b;

	private String[] c;

	private String[] d;

	private String[] correctanswer;

	private String examtypeid;

	private String studentid;

	private String examinfoname;


	public String[] getExamid() {
		return examid;
	}

	public void setExamid(String[] examid) {
		this.examid = examid;
	}

	public String[] getA() {
		return a;
	}

	public void setA(String[] a) {
		this.a = a;
	}

	public String[] getB() {
		return b;
	}

	public void setB(String[] b) {
		this.b = b;
	}

	public String[] getC() {
		return c;
	}

	public void setC(String[] c) {
		this.c = c;
	}

	public String[] getD() {
		return d;
	}

	public void setD(String[] d) {
		this.d = d;
	}

	public String[] getCorrectanswer() {
		return correctanswer;
	}

	public void setCorrectanswer(String[] correctanswer) {
		this.correctanswer = correctanswer;
	}

	public String getExamtypeid() {
		return examtypeid;
	}

	public void setExamtypeid(String examtypeid) {
		this.examtypeid = examtypeid;
	}

	public String getStudentid() {
		return studentid;
	}

	public void setStudentid(String studentid) {
		this.studentid = studentid;
	}

	public String getExamInfoname() {
		return examinfoname;
	}

	public void setExamInfoname(String examinfoname) {
		this.examinfoname = examinfoname;
	}
}
