//package com.sinosoft.sinoep.modules.实际地址.entity;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

/**
 * Entity 类
 * @author 子火
 * @Date ${cur_time?datetime}
 */
@Entity
@Table(name = "${table_name}")
/**${table_comments!}*/
public class ${entity_name} implements java.io.Serializable {
<#list col_list as colmap>
	<#if (colmap.DATA_TYPE=="NUMBER")>
		<#if ((colmap.DATA_SCALE!20) >0)>
			<#assign dataType="Double"/>
		<#else>
			<#assign dataType="Integer"/>
		</#if>
	<#elseif (colmap.DATA_TYPE=="VARCHAR2")>
		<#assign dataType="String"/>
	</#if>
	<#if (colmap.NULLABLE=="N")>
		<#assign nullAble="false"/>
	<#else>
		<#assign nullAble="true"/>
	</#if>

	/**${colmap.COMMENTS!}*/
	private ${dataType} ${colmap.CAPITALIZE};
	<#if (colmap.INITCAP=="Id")>
	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	</#if>
	<#if colmap.DATA_TYPE=="NUMBER">
	@Column(name="${colmap.COLUMN_NAME}",nullable=${nullAble},precision=${colmap.DATA_PRECISION!20}, scale=${colmap.DATA_SCALE!38})
	<#else>
	@Column(name ="${colmap.COLUMN_NAME}",nullable=${nullAble},length=${colmap.CHAR_LENGTH})
	</#if>
	public ${dataType} get${colmap.INITCAP}(){
		return this.${colmap.CAPITALIZE};
	}
	public void set${colmap.INITCAP}(${dataType} ${colmap.CAPITALIZE}){
		this.${colmap.CAPITALIZE} = ${colmap.CAPITALIZE};
	}
</#list>
	private String cz = "";
	@Transient
	public String getCz() {
		return cz;
	}
	public void setCz(String cz) {
		this.cz = cz;
	}
	public ${entity_name}(){ super();}
}