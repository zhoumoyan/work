package site.zhongkai.ask.utils;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

// 响应结果
@Data
@Accessors(chain = true)
public class ResponseResult<T> implements Serializable {
	private static final long serialVersionUID = 5010640675624139650L;
	private boolean success;
	private Integer state;
	private String message;
	private T data;

	public ResponseResult() {
	}

	public ResponseResult(boolean success, Integer state, String message) {
		this.success = success;
		this.state = state;
		this.message = message;
	}

	public ResponseResult(boolean success, Integer state, String message, T data) {
		this.success = success;
		this.state = state;
		this.message = message;
		this.data = data;
	}

}
