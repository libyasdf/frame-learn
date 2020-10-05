<template>

  <div>
    <h1>routercom</h1>
    <h2>store:{{this.$store.state.text}}</h2>
    <img src="imgurl"/>
  </div>

</template>

<script>
  // imgUrl = require('./assets/logo.png')
  export default {
    data: () => ({
      imgurl: "../static/img/test.gif"
    })
  }

</script>

<style lang="less">
h2 {
    color: red;
}
</style>
