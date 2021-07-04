<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="商品id" prop="productId">
      <el-input v-model="dataForm.productId" placeholder="商品id"></el-input>
    </el-form-item>
    <el-form-item label="秒杀价格" prop="seckillPrice">
      <el-input v-model="dataForm.seckillPrice" placeholder="秒杀价格"></el-input>
    </el-form-item>
    <el-form-item label="秒杀库存" prop="seckillStock">
      <el-input v-model="dataForm.seckillStock" placeholder="秒杀库存"></el-input>
    </el-form-item>
    <el-form-item label="秒杀时间id" prop="timeId">
      <el-input v-model="dataForm.timeId" placeholder="秒杀时间id"></el-input>
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
          seckillId: 0,
          productId: '',
          seckillPrice: '',
          seckillStock: '',
          timeId: ''
        },
        dataRule: {
          productId: [
            { required: true, message: '商品id不能为空', trigger: 'blur' }
          ],
          seckillPrice: [
            { required: true, message: '秒杀价格不能为空', trigger: 'blur' }
          ],
          seckillStock: [
            { required: true, message: '秒杀库存不能为空', trigger: 'blur' }
          ],
          timeId: [
            { required: true, message: '秒杀时间id不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.seckillId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.seckillId) {
            this.$http({
              url: this.$http.adornUrl(`/seckill/seckillproduct/info/${this.dataForm.seckillId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.productId = data.seckillProduct.productId
                this.dataForm.seckillPrice = data.seckillProduct.seckillPrice
                this.dataForm.seckillStock = data.seckillProduct.seckillStock
                this.dataForm.timeId = data.seckillProduct.timeId
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
              url: this.$http.adornUrl(`/seckill/seckillproduct/${!this.dataForm.seckillId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'seckillId': this.dataForm.seckillId || undefined,
                'productId': this.dataForm.productId,
                'seckillPrice': this.dataForm.seckillPrice,
                'seckillStock': this.dataForm.seckillStock,
                'timeId': this.dataForm.timeId
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
