<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="下单id" prop="orderId">
      <el-input v-model="dataForm.orderId" placeholder="下单id"></el-input>
    </el-form-item>
    <el-form-item label="用户id" prop="userId">
      <el-input v-model="dataForm.userId" placeholder="用户id"></el-input>
    </el-form-item>
    <el-form-item label="商品id" prop="productId">
      <el-input v-model="dataForm.productId" placeholder="商品id"></el-input>
    </el-form-item>
    <el-form-item label="商品名" prop="productNum">
      <el-input v-model="dataForm.productNum" placeholder="商品名"></el-input>
    </el-form-item>
    <el-form-item label="商品价格" prop="productPrice">
      <el-input v-model="dataForm.productPrice" placeholder="商品价格"></el-input>
    </el-form-item>
    <el-form-item label="下单时间" prop="orderTime">
      <el-input v-model="dataForm.orderTime" placeholder="下单时间"></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          id: 0,
          orderId: '',
          userId: '',
          productId: '',
          productNum: '',
          productPrice: '',
          orderTime: ''
        },
        dataRule: {
          orderId: [
            { required: true, message: '下单id不能为空', trigger: 'blur' }
          ],
          userId: [
            { required: true, message: '用户id不能为空', trigger: 'blur' }
          ],
          productId: [
            { required: true, message: '商品id不能为空', trigger: 'blur' }
          ],
          productNum: [
            { required: true, message: '商品名不能为空', trigger: 'blur' }
          ],
          productPrice: [
            { required: true, message: '商品价格不能为空', trigger: 'blur' }
          ],
          orderTime: [
            { required: true, message: '下单时间不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/order/order/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.orderId = data.order.orderId
                this.dataForm.userId = data.order.userId
                this.dataForm.productId = data.order.productId
                this.dataForm.productNum = data.order.productNum
                this.dataForm.productPrice = data.order.productPrice
                this.dataForm.orderTime = data.order.orderTime
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/order/order/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'orderId': this.dataForm.orderId,
                'userId': this.dataForm.userId,
                'productId': this.dataForm.productId,
                'productNum': this.dataForm.productNum,
                'productPrice': this.dataForm.productPrice,
                'orderTime': this.dataForm.orderTime
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
