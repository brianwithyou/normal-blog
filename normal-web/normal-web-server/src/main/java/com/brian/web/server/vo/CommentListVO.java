package com.brian.web.server.vo;

import com.brian.common.core.PageParam;
import lombok.Data;

/**
 * @author Brian
 * @date 2023/5/21
 **/
@Data
public class CommentListVO extends PageParam {

    private String userAvatar;
    private Long parentId;
    private String createTime;

//    comments: [
//    {
//        username:'Lana Del Rey',
//                id:19870621,
//            avatar:'https://ae01.alicdn.com/kf/Hdd856ae4c81545d2b51fa0c209f7aa28Z.jpg',
//            parentName:'', // 父评论名
//            parentId:'', // 父评论id
//            comment:'我发布一张新专辑Norman Fucking Rockwell,大家快来听啊',
//            time:'2019年9月16日 18:43',
//            commentNum:2, // 该评论的回复条数
//            like:15, // 点赞
//            likeFlag: true, // 点赞图标状态颜色变化
//            inputShow:false, // 输入框隐藏
//            reply:[
//        {
//            username:'Taylor Swift',
//                    id:19891221,
//                avatar:'https://ae01.alicdn.com/kf/H94c78935ffa64e7e977544d19ecebf06L.jpg',
//                parentName:'Lana Del Rey',
//                parentId:19870621,
//                comment:'我很喜欢你的新专辑！！',
//                time:'2019年9月16日 18:43',
//                commentNum:1,
//                like:15,
//                likeFlag: true, // 点赞图标状态颜色变化
//                inputShow:false
//        }
//          ]
//    },
//            ],
    private Long blogId;

    /**
     * 留言板，评论，回复
     */
    private String type;

    private Long rootId;

    private String username;


}
