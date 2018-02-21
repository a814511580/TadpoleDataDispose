package com.pear.cdn.speedycloud;

import org.json.JSONException;

/*
        url: ���ϴ�������洢�Ķ����ur�ˣ����
        address: ��Դ�ĵ�ַ
        bucket: Ŀ��Ͱ
        host: Ŀ��Ͱ��host

 * */

public class video_source {
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBucket() {
		return bucket;
	}
	public void setBucket(String bucket) {
		this.bucket = bucket;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	private String url;
	private String address;
	private String bucket;
	private String host;
	private String access_key;
	private String secret_key;
	
    public video_source(String host,String url,String address,String bucket,String access_key,String secret_key) {
    	this.url = url;
    	this.address = address;
    	this.bucket = bucket;
    	this.host = host;
        this.access_key = access_key;
        this.secret_key = secret_key;
    }
    public String InitMysqlApi(String initmysqlapiurl) throws Exception {
    	SpeedyCloudS3 sqlapi = new SpeedyCloudS3(initmysqlapiurl,"","");
        String init = sqlapi.InitMysql( url, address,  bucket, host,access_key,secret_key);
		return init;
    }
}