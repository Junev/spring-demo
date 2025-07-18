package com.example.repository.mapper;

import com.example.repository.model.AppConfigItems;
import com.example.repository.model.AppConfigItemsExample;
import com.example.repository.model.AppConfigItemsKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppConfigItemsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table APP_CONFIG_ITEMS
     *
     * @mbg.generated Fri May 16 11:19:14 CST 2025
     */
    long countByExample(AppConfigItemsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table APP_CONFIG_ITEMS
     *
     * @mbg.generated Fri May 16 11:19:14 CST 2025
     */
    int deleteByExample(AppConfigItemsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table APP_CONFIG_ITEMS
     *
     * @mbg.generated Fri May 16 11:19:14 CST 2025
     */
    int deleteByPrimaryKey(AppConfigItemsKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table APP_CONFIG_ITEMS
     *
     * @mbg.generated Fri May 16 11:19:14 CST 2025
     */
    int insert(AppConfigItems row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table APP_CONFIG_ITEMS
     *
     * @mbg.generated Fri May 16 11:19:14 CST 2025
     */
    int insertSelective(AppConfigItems row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table APP_CONFIG_ITEMS
     *
     * @mbg.generated Fri May 16 11:19:14 CST 2025
     */
    List<AppConfigItems> selectByExample(AppConfigItemsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table APP_CONFIG_ITEMS
     *
     * @mbg.generated Fri May 16 11:19:14 CST 2025
     */
    AppConfigItems selectByPrimaryKey(AppConfigItemsKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table APP_CONFIG_ITEMS
     *
     * @mbg.generated Fri May 16 11:19:14 CST 2025
     */
    int updateByExampleSelective(@Param("row") AppConfigItems row, @Param("example") AppConfigItemsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table APP_CONFIG_ITEMS
     *
     * @mbg.generated Fri May 16 11:19:14 CST 2025
     */
    int updateByExample(@Param("row") AppConfigItems row, @Param("example") AppConfigItemsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table APP_CONFIG_ITEMS
     *
     * @mbg.generated Fri May 16 11:19:14 CST 2025
     */
    int updateByPrimaryKeySelective(AppConfigItems row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table APP_CONFIG_ITEMS
     *
     * @mbg.generated Fri May 16 11:19:14 CST 2025
     */
    int updateByPrimaryKey(AppConfigItems row);
}