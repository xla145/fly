package com.xula.base.constant;

/**
 * 
 * 上传图片分类
 * 
 *
 * @author caixb
 */
public enum ImgCategory {
	
	product(1,"商品图片","yue-product/img/"),sys(2,"系统图片","yue-sys/img/"),user(3,"用户图片","yue-user/img/"),test(4,"测试图片","test/img/");

	private Integer type;
	private String name;
	private String dir;

	ImgCategory(Integer type, String name, String dir) {
		this.type = type;
		this.name = name;
		this.dir = dir;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public static String getUploadDir(Integer type){
		ImgCategory[] arr = ImgCategory.values();
		for (ImgCategory ucc : arr) {
			if (ucc.getType() == type) {
				return ucc.getDir();
			}
		}
		return "";
	}
}
