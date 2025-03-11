import axios from "axios";

export default class ApiService {
    static BASE_URL = "http://localhost:4040";

    static getHeader(){
        const token = localStorage.getItem("token");
        return {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json"
        };
    }

    //Auth && user API
    static async registerUser(registration) {
       const response = await axios.post(`${this.BASE_URL}/auth/register`, registration);
       return response.data;
    }

    static async loginUser(loginDetails) {
       const response = await axios.post(`${this.BASE_URL}/auth/login`, loginDetails);
       return response.data;
    }

    static async getAllUsers(){
        const response = await axios.get(`${this.BASE_URL}/user/get-all`,{
            headers: this.getHeader()
        });
        return response.data;
    }

    static async getLoggedInUserInfo(){
        const response = await axios.get(`${this.BASE_URl}/user/my-info`,{
            headers: this.getHeader()
        });
        return response.data;
    }


    /** Product API **/
    static async addProduct(formData) {
        const response = await axios.post(`${this.BASE_URL}/product/create`, formData,{
            headers:{
                ...this.getHeader(),
                "Content-Type": "multipart/form-data"
            }
        });
        return response.data;
    }


    static async updateProduct(formData) {
       const response = await axios.put(`${this.BASE_URL}/product/update`, formData,{
           headers:{
               ...this.getHeader(),
               "Content-Type": "multipart/form-data"
           }
       });
       return response.data;
    }

    static async getAllProducts() {
       const response = await axios.get(`${this.BASE_URL}/product/get-all`);
       return response.data;
    }

    static async searchProduct(searchValue) {
       const response = await axios.get(`${this.BASE_URL}/product/get-all`, {
           params: {searchValue}
       });
       return response.data;
    }

    static async getAllProductsByCategoryId(categoryId) {
        const response = await axios.get(`${this.BASE_URL}/product/get-by-category/${categoryId}`);
        return response.data;
    }

    static async getProductById(productId) {
        const response = await axios.get(`${this.BASE_URL}/product/get-by-id/${productId}`);
        return response.data;
    }

    static async deleteProduct(productId) {
        const response = await axios.get(`${this.BASE_URL}/product/get-by-id/${productId}`,{
            headers:this.getHeader()
        });
        return response.data;
    }

    /** Category API **/
    static async createCategory(body) {
        const response = await axios.post(`${this.BASE_URL}/product/create`, body,{
            headers:this.getHeader()
        })
        return response.data;
    }

    static async updateCategory(categoryId,body) {
        const response = await axios.put(`${this.BASE_URL}/product/update/${categoryId}`, body,{
            headers:this.getHeader()
        });
        return response.data;
    }

    static async deleteCategory(categoryId) {
        const response = await axios.delete(`${this.BASE_URL}/product/delete/${categoryId}`,{
            headers:this.getHeader()
        });
        return response.data;
    }

    static async getAllCategories(){
        const response = await axios.get(`${this.BASE_URL}/categories/get-all`);
        return response.data;
    }

    static async getCategoryById(categoryId) {
        const response = await axios.get(`${this.BASE_URL}/categories/get-by-id/${categoryId}`);
        return response.data;
    }

    /** Address API **/
    static async saveAddress(address) {
        const response = await axios.post(`${this.BASE_URL}/address/save`, address,{
            headers:this.getHeader()
        })
        return response.data;
    }

    /** Order API **/
    static async placeOrder(body){
        const response = await axios.post(`${this.BASE_URL}/order-item/place-order`,body,{
            headers:this.getHeader()
        });
        return response.data;
    }

    static async updateOrder(orderId, status){
        const response = await axios.put(`${this.BASE_URL}/order-item/update/${orderId}`,{
            headers:this.getHeader(),
            params:{status}
        })
        return response.data;
    }

    static async getAllOrders() {
        const response = await axios.get(`${this.BASE_URL}/order-item/filter`,{
            headers:this.getHeader()
        });
        return response.data;
    }

    static async getOrderById(orderId){
        const response = await axios.get(`${this.BASE_URL}/order-item/filter`,{
            headers:this.getHeader(),
            params:{orderId}
        });
        return response.data;
    }

    static async getAllOrderItemsByStatus(status){
        const response = await axios.get(`${this.BASE_URL}/order-item/filter`,{
            headers:this.getHeader(),
            params:{status}
        });
        return response.data;
    }

    /** Authentication Checker **/
    static logout(){
        localStorage.removeItem('token');
        localStorage.removeItem('role');
    }

    static  isAuthenticated(){
        const token = localStorage.getItem('token');
        return !!token;
    }
    static  isAdmin(){
        const role = localStorage.getItem('role');
        return role === 'admin';
    }

}