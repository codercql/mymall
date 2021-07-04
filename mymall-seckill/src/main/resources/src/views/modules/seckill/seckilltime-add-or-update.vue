<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="秒杀开始时间" prop="startTime">
      <el-input v-model="dataForm.startTime" placeholder="秒杀开始时间"></el-input>
    </el-form-item>
    <el-form-item label="秒杀结束时间" prop="endTime">
      <el-input v-model="dataForm.endTime" placeholder="秒杀结束时间"></el-input>
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
          timeId: 0,
          startTime: '',
          endTime: ''
        },
        dataRule: {
          startTime: [
            { required: true, message: '秒杀开始时间不能为空', trigger: 'blur' }
          ],
          endTime: [
            { required: true, message: '秒杀结束时间不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.timeId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.timeId) {
            this.$http({
              url: this.$http.adornUrl(`/seckill/seckilltime/info/${this.dataForm.timeId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.startTime = data.seckillTime.startTime
                this.dataForm.endTime = data.seckillTime.endTime
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
              url: this.$http.adornUrl(`/seckill/seckilltime/${!this.dataForm.timeId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'timeId': this.dataForm.timeId || undefined,
                'startTime': this.dataForm.startTime,
                'endTime': this.dataForm.endTime
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
