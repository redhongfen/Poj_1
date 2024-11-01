import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        cart: [] // 购物车的初始状态
    },
    mutations: {
        ADD_TO_CART(state, item) {
            const foundItem = state.cart.find(cartItem => cartItem.id === item.id);
            if (foundItem) {
                foundItem.quantity += 1; // 如果物品已在购物车中，则增加数量
            } else {
                state.cart.push({ ...item, quantity: 1 }); // 否则，将新物品添加到购物车
            }
        },
        REMOVE_FROM_CART(state, item) {
            const foundItem = state.cart.find(cartItem => cartItem.id === item.id);
            if (foundItem) {
                if (foundItem.quantity > 1) {
                    foundItem.quantity -= 1; // 如果数量大于1，减少数量
                } else {
                    state.cart = state.cart.filter(cartItem => cartItem.id !== item.id); // 否则从购物车中移除物品
                }
            }
        }
    },
    actions: {
        addToCart({ commit }, item) {
            commit('ADD_TO_CART', item);
        },
        removeFromCart({ commit }, item) {
            commit('REMOVE_FROM_CART', item);
        }
    },
    getters: {
        cartItems: state => state.cart,
        cartItemCount: state => state.cart.length,
        getItemQuantity: (state) => (id) => {
            const foundItem = state.cart.find(item => item.id === id);
            return foundItem ? foundItem.quantity : 0; // 如果找到了，返回数量；否则返回 0
        }
    }
});
