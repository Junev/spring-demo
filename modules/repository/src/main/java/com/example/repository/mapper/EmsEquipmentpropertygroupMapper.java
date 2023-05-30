package com.example.repository.mapper;

import com.example.repository.model.EmsEquipmentpropertygroup;
import com.example.repository.model.EmsEquipmentpropertygroupExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmsEquipmentpropertygroupMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ems_equipmentpropertygroup
     *
     * @mbg.generated Tue May 30 17:21:52 GMT+08:00 2023
     */
    long countByExample(EmsEquipmentpropertygroupExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ems_equipmentpropertygroup
     *
     * @mbg.generated Tue May 30 17:21:52 GMT+08:00 2023
     */
    int deleteByExample(EmsEquipmentpropertygroupExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ems_equipmentpropertygroup
     *
     * @mbg.generated Tue May 30 17:21:52 GMT+08:00 2023
     */
    int deleteByPrimaryKey(String propertygroupid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ems_equipmentpropertygroup
     *
     * @mbg.generated Tue May 30 17:21:52 GMT+08:00 2023
     */
    int insert(EmsEquipmentpropertygroup row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ems_equipmentpropertygroup
     *
     * @mbg.generated Tue May 30 17:21:52 GMT+08:00 2023
     */
    int insertSelective(EmsEquipmentpropertygroup row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ems_equipmentpropertygroup
     *
     * @mbg.generated Tue May 30 17:21:52 GMT+08:00 2023
     */
    List<EmsEquipmentpropertygroup> selectByExample(EmsEquipmentpropertygroupExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ems_equipmentpropertygroup
     *
     * @mbg.generated Tue May 30 17:21:52 GMT+08:00 2023
     */
    EmsEquipmentpropertygroup selectByPrimaryKey(String propertygroupid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ems_equipmentpropertygroup
     *
     * @mbg.generated Tue May 30 17:21:52 GMT+08:00 2023
     */
    int updateByExampleSelective(@Param("row") EmsEquipmentpropertygroup row, @Param("example") EmsEquipmentpropertygroupExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ems_equipmentpropertygroup
     *
     * @mbg.generated Tue May 30 17:21:52 GMT+08:00 2023
     */
    int updateByExample(@Param("row") EmsEquipmentpropertygroup row, @Param("example") EmsEquipmentpropertygroupExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ems_equipmentpropertygroup
     *
     * @mbg.generated Tue May 30 17:21:52 GMT+08:00 2023
     */
    int updateByPrimaryKeySelective(EmsEquipmentpropertygroup row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ems_equipmentpropertygroup
     *
     * @mbg.generated Tue May 30 17:21:52 GMT+08:00 2023
     */
    int updateByPrimaryKey(EmsEquipmentpropertygroup row);
}