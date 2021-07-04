<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="轮播图片" prop="imgPath">
      <el-input v-model="dataForm.imgPath" placeholder="轮播图片"></el-input>
    </el-form-item>
    <el-form-item label="轮播图描述" prop="describes">
      <el-input v-model="dataForm.describes" placeholder="轮播图描述"></el-input>
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
          carouselId: 0,
          imgPath: '',
          describes: ''
        },
        dataRule: {
          imgPath: [
            { required: true, message: '轮播图片不能为空', trigger: 'blur' }
          ],
          describes: [
            { required: true, message: '轮播图描述不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.carouselId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.carouselId) {
            this.$http({
              url: this.$http.adornUrl(`/product/carousel/info/${this.dataForm.carouselId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.imgPath = data.carousel.imgPath
                this.dataForm.describes = data.carousel.describes
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
              url: this.$http.adornUrl(`/product/carousel/${!this.dataForm.carouselId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'carouselId': this.dataForm.carouselId || undefined,
                'imgPath': this.dataForm.imgPath,
                'describes': this.dataForm.describes
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
