package com.bgarage.ims.inventory.crudport.models;

public class ResponseBean {

	private int statusCode;

	private String status;

	private String message;

	private Object data;

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public static final class ResponseBuilder {
		private int statusCode;
		private String status;
		private String message;
		private Object data;

		private ResponseBuilder() {
		}

		public static ResponseBuilder response() {
			return new ResponseBuilder();
		}

		public ResponseBuilder withStatusCode(int statusCode) {
			this.statusCode = statusCode;
			return this;
		}

		public ResponseBuilder withStatus(String status) {
			this.status = status;
			return this;
		}

		public ResponseBuilder withMessage(String message) {
			this.message = message;
			return this;
		}

		public ResponseBuilder withData(Object data) {
			this.data = data;
			return this;
		}

		public ResponseBean build() {
			ResponseBean response = new ResponseBean();
			response.setStatusCode(statusCode);
			response.setStatus(status);
			response.setMessage(message);
			response.setData(data);
			return response;
		}
	}
}
