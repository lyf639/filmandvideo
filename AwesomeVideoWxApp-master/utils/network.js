import { globalUrls } from "urls.js"

const network = {
  //获取电影列表
  getMovieList: function (params) {
    params.type = 'movie'
    this.getItemList(params)
  },
  //获取电视剧列表
  getTVList: function (params) {
    params.type = 'tv'
    this.getItemList(params)
  },
  //获取综艺列表
  getShowList: function (params) {
    params.type='show'
    this.getItemList(params)
  },
  //获取item列表
  getItemList: function(params){
    var url='';
    if(params.type==='movie'){
      url=globalUrls.movieList
    }else if(params.type==='tv'){
      url=globalUrls.tvList
    }else{
      url=globalUrls.showList
    }
    var count=params.count?params.count:7
    wx.request({
      url: url,
      data: {
        count: count
      },
      success: function (res) {
        var items = res.data.subject_collection_items
        var itemCount=items.length
        var left=itemCount%3
        if(left==2){
          items.push(null)
        }
        if (params && params.success) {
          params.success(items);
        }
      }
    })
  },
  //获取详情第一部分
  getItemDetail:function(params){
    var type=params.type
    var id=params.id
    var url=''
    if(type==='movie'){
      url =globalUrls.movieDetail+id
    }else if(type==='tv'){
      url = globalUrls.tvDetail+id
    }else{
      url = globalUrls.showDetail+id
    }
    wx.request({
      url: url,
      success:function(res){
        if(params.success){
          params.success(res.data)
        }
      }
    })
  },
  //获取详情页第二部分,标签
  getItemTags:function(params){
    var type = params.type
    var id = params.id
    var url = ''  
    if (type === 'movie') {
      url = globalUrls.movieTags(id)
    } else if (type === 'tv') {
      url = globalUrls.tvTags(id)
    } else {
      url = globalUrls.showTags(id)
    }
    wx.request({
      url: url,
      success:function(res){
        if(params.success){
          params.success(res.data.tags)
        }
      }
    })
  },
  //获取详情页第三部分，评论
  getItemComments:function(params){
    var type = params.type
    var id = params.id
    var url = '' 
    var start = params.start ? params.start : 0;
    var count = params.count ? params.count : 3;
    if (type === 'movie') {
      url = globalUrls.movieComments(id,start,count)
    } else if (type === 'tv') {
      url = globalUrls.tvComments(id, start, count)
    } else {
      url = globalUrls.showComments(id, start, count)
    }
    wx.request({
      url: url,
      success:function(res){
        if(params.success){
          params.success(res.data)
        }
      }
    })
  },

  // 搜索item
  getSearch: function (params) {
    var q = params.q;
    var url = globalUrls.searchUrl(q);
    wx.request({
      url: url,
      success: function (res) {
        var subjects = res.data.subjects;
        if (params.success) {
          params.success(subjects);
        }
      }
    })
  }




}

export { network }