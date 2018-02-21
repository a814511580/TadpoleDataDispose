package com.pear.cdn.speedycloud;

/*
 *         bucket: Ŀ��Ͱ
        host: Ŀ��Ͱ��host
        resolutions: �ֱ��ʣ�����á������ָ������磺480P1,480P2,720P
        callback_url: �ص�url
        source_id: �ϸ��ӿڷ��ص�source_id
 * */

public class TranscodeFinal {
	private String data;
	public TranscodeFinal(String data) {
		this.data = data;
	}
	
    public String Process(String transurl) throws Exception {
    	  SpeedyCloudS3 pfinal = new SpeedyCloudS3(transurl,"","");
    	  String ret = pfinal.FinalProcess(data);
    	  return ret;
    }

}