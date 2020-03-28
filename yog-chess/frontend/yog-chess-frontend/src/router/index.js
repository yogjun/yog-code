import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import Chat from '@/components/Chat'
import SimpleChess from '@/components/SimpleChess'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'HelloWorld',
      component: HelloWorld
    },
    {
      path: '/chat',
      name: 'Chat',
      component: Chat
    },
    {
      path: '/simpleChess',
      name: 'SimpleChess',
      component: SimpleChess
    }
  ]
})
