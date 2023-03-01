package nuc.zm.rabbit_demo.mapper;

import nuc.zm.rabbit_demo.po.OrderDetailPo;
import org.apache.ibatis.annotations.*;

/**
 * 订单细节映射器
 *
 * @author zm
 * @date 2023/03/01
 */
@Mapper
public interface OrderDetailMapper {

    /**
     * 插入订单细节
     *
     * @param order 订单
     */
    @Insert("insert into order_detail" +
            " (status, address, account_id, product_id, deliveryman_id, settlement_id, reward_id, price, date) " +
            "VALUES (#{status},#{address},${accountId},#{productId},#{deliverymanId},#{settlementId},#{rewardId},#{price},#{date}) ")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    void insertOrderDetail(OrderDetailPo order);


    /**
     * 更新订单细节
     *
     * @param oderDetailPo 奥得河详细订单
     */

    @Update("update order_detail SET status = #{status},address= #{address} ," +
            "account_id = #{accountId}, product_id = #{productId} " +
            ",deliveryman_id = #{deliverymanId} , " +
            "settlement_id = #{settlementId} , reward_id = #{rewardId}, " +
            "price = #{price}, date = #{date}" +
            " where id = #{id}")
    void updateOrderDetail(OrderDetailPo oderDetailPo);


    /**
     * 选择奥得河细节通过id
     *
     * @param id id
     * @return {@link OrderDetailPo}
     */
    @Select("SELECT * FROM order_detail where  id = #{id}")
    OrderDetailPo selectOderDetailById(Integer id);
}
