package com.example.repository.mapper;

import com.example.repository.model.EmsEquipmentproperty;
import com.example.repository.model.EmsEquipmentpropertyExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmsEquipmentpropertyMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ems_equipmentproperty
     *
     * @mbg.generated Tue May 30 17:21:52 GMT+08:00 2023
     */
    long countByExample(EmsEquipmentpropertyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ems_equipmentproperty
     *
     * @mbg.generated Tue May 30 17:21:52 GMT+08:00 2023
     */
    int deleteByExample(EmsEquipmentpropertyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ems_equipmentproperty
     *
     * @mbg.generated Tue May 30 17:21:52 GMT+08:00 2023
     */
    int insert(EmsEquipmentproperty row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ems_equipmentproperty
     *
     * @mbg.generated Tue May 30 17:21:52 GMT+08:00 2023
     */
    int insertSelective(EmsEquipmentproperty row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ems_equipmentproperty
     *
     * @mbg.generated Tue May 30 17:21:52 GMT+08:00 2023
     */
    List<EmsEquipmentproperty> selectByExample(EmsEquipmentpropertyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ems_equipmentproperty
     *
     * @mbg.generated Tue May 30 17:21:52 GMT+08:00 2023
     */
    int updateByExampleSelective(@Param("row") EmsEquipmentproperty row, @Param("example") EmsEquipmentpropertyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ems_equipmentproperty
     *
     * @mbg.generated Tue May 30 17:21:52 GMT+08:00 2023
     */
    int updateByExample(@Param("row") EmsEquipmentproperty row, @Param("example") EmsEquipmentpropertyExample example);
}