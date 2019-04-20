const app = getApp();
Page({
  data: {
    //判断小程序的API，回调，参数，组件等是否在当前版本可用。
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    active: 0,
    nickName: '微信授权登录',
    avatarUrl: '../../package/head.png',
    openid: undefined
  },
  onLoad: function(options) {
    var that = this;
    that.setData({
      active: options.active
    })
    
    // 查看是否授权
    // wx.getSetting({
    //   success: function (res) {
    //     if (res.authSetting['scope.userInfo']) {
    //       wx.getUserInfo({
    //         success: function (res) {
    //           //从数据库获取用户信息
    //           that.queryUsreInfo();
    //           //用户已经授权过
    //           wx.switchTab({
    //             url: '../translation/index'
    //           })
    //         }
    //       });
    //     }
    //   }
    // })

    wx.getSetting({
      success(res) {
        if (res.authSetting['scope.userInfo']) {
          // 已经授权，可以直接调用 getUserInfo 获取头像昵称
          wx.getUserInfo({
            success(res) {
              console.log(res.userInfo)
              that.setData({
                nickName: res.userInfo.nickName,
                avatarUrl: res.userInfo.avatarUrl
              })
            }
          })
        }
      }
    })

  },
  showModal(e) {
    this.setData({
      modalName: e.currentTarget.dataset.target
    })
  },
  hideModal(e) {
    this.setData({
      modalName: null
    })
  },
  menuBorder: function(e) {
    this.setData({
      menuBorder: e.detail.value
    });
  },
  menuArrow: function(e) {
    this.setData({
      menuArrow: e.detail.value
    });
  },
  menuCard: function(e) {
    this.setData({
      menuCard: e.detail.value
    });
  },

  bindGetUserInfo: function(e) {
    // if (e.detail.userInfo) {
    //   //用户按了允许授权按钮
    //   var that = this;
    //   app.globalData.userInfo = e.detail.userInfo;

    //   //插入登录的用户的相关信息到数据库
    //   wx.request({
    //     url: app.globalData.urlPath + 'user/add',
    //     data: {
    //       openid: app.globalData.openid,
    //       nickName: e.detail.userInfo.nickName,
    //       avatarUrl: e.detail.userInfo.avatarUrl,
    //       province: e.detail.userInfo.province,
    //       city: e.detail.userInfo.city
    //     },
    //     header: {
    //       'content-type': 'application/json'
    //     },
    //     success: function(res) {
    //       //从数据库获取用户信息
    //       // that.queryUsreInfo();
    //       console.log("插入小程序登录用户信息成功！");
    //     }
    //   });
    //   //授权成功后，跳转进入小程序首页
    //   wx.switchTab({
    //     url: '../translation/index'
    //   })
    // } else {
    //   //用户按了拒绝按钮
    //   wx.showModal({
    //     title: '警告',
    //     content: '您点击了拒绝授权，将无法进入小程序，请授权之后再进入!!!',
    //     showCancel: false,
    //     confirmText: '返回授权',
    //     success: function(res) {
    //       if (res.confirm) {
    //         console.log('用户点击了“返回授权”')
    //       }
    //     }
    //   })
    // }
    console.log(e.detail.userInfo)
    console.log('openid为' + app.globalData.openid)
    this.setData({
      nickName: e.detail.userInfo.nickName,
      avatarUrl: e.detail.userInfo.avatarUrl,
      openid: app.globalData.openid
    })


  },
  //获取用户信息接口
  queryUsreInfo: function() {
    wx.request({
      url: app.globalData.urlPath + 'user/userInfo',
      data: {
        openid: app.globalData.openid
      },
      header: {
        'content-type': 'application/json'
      },
      success: function(res) {
        app.globalData.userInfo = res.data;
      }
      
    });
  },

  onChange(event) {
    console.log(event.detail);
    if (event.detail == 0) {
      wx.redirectTo({
        url: '../translation/index?active=' + event.detail,
      })
    }
    if (event.detail == 1) {
      wx.redirectTo({
        url: '../news/news?active=' + event.detail,
      })

    }
    if (event.detail == 2) {
      wx.redirectTo({
        url: '../list/list?active=' + event.detail,
      })
    }
  },
})