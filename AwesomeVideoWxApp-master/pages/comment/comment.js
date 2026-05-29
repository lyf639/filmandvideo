import {network} from '../../utils/network.js'
Page({

  /**
   * 页面的初始数据
   */
  data: {
    start:1,
    count:20,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    //console.log(options)
    var id=options.id
    var type=options.type
    // var thumbnail=options.thumbnail
    // var title=options.title
    // var rate=options.rate
    var that=this
    that.setData(options)
    //获取评论
    network.getItemComments({
      type:type,
      id:id,
      start:that.data.start,
      count:that.data.count,
      success:function(comments){
        console.log(comments)
        that.setData({
          comments:comments.interests,
          total:comments.total
        })
      }
    })

  },

  //上一页
  onPrePageTap:function(){
    var that = this
    that.setData({
      preLoading: true,
    })
    network.getItemComments({
      type: that.data.type,
      id: that.data.id,
      start: that.data.start - 20,
      count: that.data.count,
      success: function (comments) {
        that.setData({
          comments: comments.interests,
          start: that.data.start - 20,
          preLoading: false,
        })
      }
    })
    wx.pageScrollTo({
      scrollTop: 0,
    })
  },
  //下一页
  onNextPageTap:function(){
    var that=this
    that.setData({
      nextLoading: true
    })
    network.getItemComments({
      type:that.data.type,
      id:that.data.id,
      start: that.data.start+20,
      count: that.data.count,
      success:function(comments){
        that.setData({
          comments: comments.interests,
          start:that.data.start+20,
          nextLoading: false
        })
      }
    })
    wx.pageScrollTo({
      scrollTop: 0,
    })
  },


  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})