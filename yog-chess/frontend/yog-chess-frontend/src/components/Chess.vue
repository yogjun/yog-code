<template>
  <div class="box">
    <canvas ref="canvas" width="450" height="450" @click="chessClick"></canvas>
  </div>
</template>

<script>
export default {
  created () { // 页面创建生命周期函数
    this.initWebSocket()
  },
  destroyed: function () { // 离开页面生命周期函数
    this.websocketClose()
  },
  data () {
    return {
      chess: {},
      context: {},
      chessBoard: [], // 记录是否走过
      me: true,
      count: 0, // 所有赢法数量
      wins: [], // 赢法数组
      myWin: [], // 我方赢法的统计数组
      computerWin: [], // 计算机赢法的统计数组
      over: false,
      webSocket: [],
      colorb: ['#0a0a0a', '#636766'],
      colorw: ['#d1d1d1', '#f9f9f9'],
      color: 'b',
      sessionId: '123'
    }
  },
  mounted () {
    setTimeout(_ => {
      this.init()
    })
  },
  methods: {
    // 初始化
    init () {
      this.getSessionId()
      this.chess = this.$refs.canvas
      this.context = this.chess.getContext('2d')
      this.drawImage(_ => {
        this.drawChessBoard()
      })
      this.fillArray()
      this.getUserColor()
    },
    initWebSocket: function () {
      // WebSocket与普通的请求所用协议有所不同，ws等同于http，wss等同于https
      this.webSocket = new WebSocket('ws://localhost:9001/chess/chessWs/DPS007')
      this.webSocket.onopen = this.websocketOnopen
      this.webSocket.onerror = this.websocketOnerror
      this.webSocket.onmessage = this.websocketOnmessage
      this.webSocket.onclose = this.websocketClose
    },
    websocketOnopen: function () {
      console.log('WebSocket连接成功')
    },
    websocketOnerror: function (e) {
      console.log('WebSocket连接发生错误')
    },
    websocketOnmessage: function (e) {
      this.onMessage(e)
    },
    websocketClose: function (e) {
      console.log('connection closed (' + e.code + ')')
    },
    getSessionId: function () {
      const request = require('request')
      const _this = this
      request('http://localhost:9001/chess/sessionId', {withCredentials: true}, function (error, response, body) {
        console.error('error:', error)
        console.log('statusCode:', response && response.statusCode)
        console.log('body:', body)
        _this.sessionId = JSON.parse(body).msg
      })
    },
    getUserColor: function () {
      const request = require('request')
      const _this = this
      request('http://localhost:9001/chess/userColor', {withCredentials: true}, function (error, response, body) {
        console.error('error:', error)
        console.log('statusCode:', response && response.statusCode)
        console.log('body:', body)
        _this.color = JSON.parse(body).msg
      })
    },
    onMessage (event) {
      let action = JSON.parse(event.data)
      const x = action.x
      const y = action.y
      const userColor = action.color
      if (this.chessBoard[x][y] === 0) {
        this.chessBoard[x][y] = 1
        this.onStep(x, y, userColor)
        this.me = true
        // 统计赢法
        for (let k = 0; k < this.count; k++) {
          if (this.wins[x][y][k]) {
            this.myWin[k]++
            this.computerWin[k] = 6
            if (this.myWin[k] === 5) {
              this.over = true
              alert('对方赢了')
            }
          }
        }
      }
    },
    // 我方落子
    chessClick (e) {
      if (this.over) {
        return
      }
      if (!this.me) {
        return
      }
      const ox = e.offsetX
      const oy = e.offsetY
      const x = Math.floor(ox / 30)
      const y = Math.floor(oy / 30)
      this.sendChessMsg(this.color, x, y)
      if (this.chessBoard[x][y] === 0) {
        this.chessBoard[x][y] = 1
        this.onStep(x, y, this.color)
        this.me = false
        // 统计赢法
        for (let k = 0; k < this.count; k++) {
          if (this.wins[x][y][k]) {
            this.myWin[k]++
            this.computerWin[k] = 6
            if (this.myWin[k] === 5) {
              this.over = true
              alert('你赢了')
            }
          }
        }
      }
    },
    // websocket发送消息
    sendChessMsg (color, x, y) {
      // eslint-disable-next-line no-new-object
      let chessAction = new Object()
      chessAction.name = 'chess'
      chessAction.color = color
      chessAction.x = x
      chessAction.y = y
      let chessInfoStr = JSON.stringify(chessAction)
      this.webSocket.send(chessInfoStr)
    },
    // 落子实现
    onStep (x, y, color) {
      debugger
      const { context } = this
      context.beginPath()
      context.arc(15 + x * 30, 15 + y * 30, 13, 0, 2 * Math.PI)
      context.closePath()
      const gradient = context.createRadialGradient(15 + x * 30 + 2, 15 + y * 30 - 2, 13, 15 + x * 30 + 2, 15 + y * 30 - 2, 0)
      if (color === 'b') {
        gradient.addColorStop(0, this.colorb[0])
        gradient.addColorStop(1, this.colorb[1])
      } else {
        gradient.addColorStop(0, this.colorw[0])
        gradient.addColorStop(1, this.colorw[1])
      }
      context.fillStyle = gradient
      context.fill()
    },
    // 填充数组
    fillArray () {
      // 是否走过
      for (let i = 0; i < 15; i++) {
        this.chessBoard[i] = []
        for (let j = 0; j < 15; j++) {
          this.chessBoard[i][j] = 0
        }
      }
      // 赢法数组
      for (let i = 0; i < 15; i++) {
        this.wins[i] = []
        for (let j = 0; j < 15; j++) {
          this.wins[i][j] = []
        }
      }
      // 横
      for (let i = 0; i < 15; i++) {
        for (let j = 0; j < 11; j++) {
          for (let k = 0; k < 5; k++) {
            this.wins[i][j + k][this.count] = true
          }
          this.count++
        }
      }
      // 竖
      for (let i = 0; i < 15; i++) {
        for (let j = 0; j < 11; j++) {
          for (let k = 0; k < 5; k++) {
            this.wins[j + k][i][this.count] = true
          }
          this.count++
        }
      }
      // 斜
      for (let i = 0; i < 11; i++) {
        for (let j = 0; j < 11; j++) {
          for (let k = 0; k < 5; k++) {
            this.wins[i + k][j + k][this.count] = true
          }
          this.count++
        }
      }
      // 反斜
      for (let i = 0; i < 11; i++) {
        for (let j = 14; j > 3; j--) {
          for (let k = 0; k < 5; k++) {
            this.wins[i + k][j - k][this.count] = true
          }
          this.count++
        }
      }
      // 赢法的统计数组
      for (let i = 0; i < this.count; i++) {
        this.myWin[i] = 0
        this.computerWin[i] = 0
      }
    },
    // 绘制水印
    drawImage (callback) {
      const { context } = this
      const img = new Image()
      // img.src = '../../images/logo.png'
      img.src = 'https://huiji-public.huijistatic.com/gbf/uploads/e/e3/Memorial_9004_m.jpg'
      img.width = 450
      img.height = 450
      img.onload = _ => {
        context.drawImage(img, (450 - img.width) / 2, (450 - img.height) / 2, img.width, img.height)
        callback()
      }
    },
    // 绘制棋盘
    drawChessBoard () {
      const { context } = this
      context.strokeStyle = '#bfbfbf'
      for (let i = 0; i < 15; i++) {
        context.moveTo(15 + i * 30, 15)
        context.lineTo(15 + i * 30, 435)
        context.stroke()
        context.moveTo(15, 15 + i * 30)
        context.lineTo(435, 15 + i * 30)
        context.stroke()
      }
    }
  },
  head () {
    return {
      title: '五子棋 | 娱乐',
      meta: [
        { hid: 'keywords', name: 'keywords', content: '五子棋' },
        { hid: 'description', name: 'description', content: '五子棋' }
      ]
    }
  }
}
</script>

<style lang="scss" scoped>
  .box{
    canvas{
      border: 1px solid #ddd;
      margin: 20px auto;
      background: #fff;
      box-shadow: -2px 0 2px #efefef, 5px 5px 5px #b9b9b9;
    }
  }
</style>
