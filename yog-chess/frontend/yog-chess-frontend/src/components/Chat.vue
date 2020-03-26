<template>
  <div>
    <h1>测试webSocket</h1>
    <button @click="getWebsocket">点击请求后台数据</button>
  </div>
</template>

<script>
export default {
  name: 'Chat',
  created () { // 页面创建生命周期函数
    this.initWebSocket()
  },
  destroyed: function () { // 离开页面生命周期函数
    this.websocketclose()
  },
  methods: {
    initWebSocket: function () {
      // WebSocket与普通的请求所用协议有所不同，ws等同于http，wss等同于https
      this.websock = new WebSocket('ws://localhost:9001/chess/chatserver?shipId=DPS007')
      this.websock.onopen = this.websocketonopen
      this.websock.onerror = this.websocketonerror
      this.websock.onmessage = this.websocketonmessage
      this.websock.onclose = this.websocketclose
    },
    websocketonopen: function () {
      console.log('WebSocket连接成功')
    },
    websocketonerror: function (e) {
      console.log('WebSocket连接发生错误')
    },
    websocketonmessage: function (e) {
      console.log(e.data) // console.log(e);
    },
    websocketclose: function (e) {
      console.log('connection closed (' + e.code + ')')
    },
    getWebsocket: function () {
      const request = require('request')
      request('http://localhost:9001/chess/chat/getInfo?shipId=DPS007', function (error, response, body) {
        console.error('error:', error) // Print the error if one occurred
        console.log('statusCode:', response && response.statusCode) // Print the response status code if a response was received
        console.log('body:', body) // Print the HTML for the Google homepage.
      })
    }
  }
}
</script>
<style lang="less" scoped>
</style>
