<template> 
<div class="">
    <div class="" style="width: 100%; margin: 10px 0;">
        总价: {{ totalPrice }} 元
    </div>

    <el-table :data="tableData" stripe :header-cell-style="{ backgroundColor: 'aliceblue', color: '#666' }">
        <el-table-column prop="id" label="ID" ></el-table-column>
        <el-table-column prop="goodsName" label="商品名"></el-table-column>
        <el-table-column prop="price" label="价格" :formatter="formatPrice"></el-table-column>
        <el-table-column prop="quantity" label="数量"></el-table-column>
        <el-table-column label="该行价格" :formatter="formatRowPrice"></el-table-column>
    </el-table>
</div>
</template>
    
<script>
import { mapGetters } from 'vuex';

export default {
    name: "GoodsView",
    data() {
        return {
            // 本地数据用于存储购物车的商品信息
            tableData: []
        };
    },
    computed: {
        ...mapGetters(['cartItems']), // 从 Vuex 中获取购物车商品
        totalPrice() {
            // 计算总价
            return this.cartItems.reduce((total, item) => {
                return total + item.price * item.quantity; // 假设每个 item 有 price 和 quantity 属性
            }, 0).toFixed(2); // 保留两位小数
        }
    },
    created() {
        this.loadCartItems(); // 在组件创建时加载购物车商品
    },
    methods: {
        loadCartItems() {
            // 将 Vuex 中的购物车商品加载到本地表格数据
            this.tableData = this.cartItems.map(item => ({
                id: item.id,
                goodsName: item.goodsName,
                price: item.price,
                quantity: item.quantity // 这里增加了数量
            }));
        },
        formatPrice(row, column, cellValue) {
            return `${cellValue} 元`; // 格式化价格显示
        },
        formatRowPrice(row) {
            // 计算并返回该行价格
            return (row.price * row.quantity).toFixed(2) + ' 元';
        }
    }
}
</script>
    
<style scoped>
/* 可以在这里添加样式 */
</style>
    