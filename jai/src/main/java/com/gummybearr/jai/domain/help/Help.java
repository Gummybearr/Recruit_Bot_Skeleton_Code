package com.gummybearr.jai.domain.help;

public class Help {
    public static final String helpDoc = "\uD83E\uDDD1\u200D\uD83D\uDCBB 채용정보 알리미의 사용법을 알려드릴게요\n" +
            "\n" +
            "1. \uD83D\uDD0D 공고 검색(search)\n" +
            "/search - 일반적인 공고를 볼 수 있습니다. 화이트리스트나 블랙리스트와 같은 필터를 걸지 않은 상태에서 공백은 아무 결과도 불러오지 않아요\n" +
            "/search {검색어} - {검색어}를 포함하는 공고를 볼 수 있습니다\n" +
            "ex) /search 초코칩 - 기업명이나 공고 내용에 초코칩이 포함되는 데이터를 볼 수 있어요 \uD83C\uDF6A\n" +
            "\n" +
            "2. \uD83D\uDE45\uD83C\uDFFB 블랙리스트(blacklist)\n" +
            "/blacklist - 현재 설정해둔 블랙리스트를 볼 수 있습니다. 블랙리스트에 있는 단어를 포함하는 공고는 사용자에게 표시되지 않아요\uD83D\uDD76\n" +
            "/blacklist add {키워드} - 키워드를 블랙리스트에 추가합니다\n" +
            "/blacklist del {키워드} - 키워드를 블랙리스트에서 제거합니다\n" +
            "ex) /blacklist add 브로콜리 - 기업명이나 공고 내용에 브로콜리가 포함된다면 사용자에게 표시되지 않아요 \uD83E\uDD66\n" +
            "\n" +
            "3. \uD83D\uDE46\uD83C\uDFFB 화이트리스트(whitelist)\n" +
            "/whitelist - 현재 설정해둔 화이트리스트를 볼 수 있습니다. 화이트리스트에 있는 단어를 포함하는 공고만 사용자에게 표시돼요\uD83D\uDC53\n" +
            "/whitelist add {키워드} - 키워드를 화이트리스트에 추가합니다\n" +
            "/whitelist del {키워드} - 키워드를 화이트리스트에서 제거합니다\n" +
            "ex) whitelist del 프레첼 - 이제는 기업명이나 공고 내용에 프레첼이 포함되지 않아도 사용자에게 표시돼요 \uD83E\uDD68\n" +
            "\n" +
            "\uD83E\uDD37\uD83C\uDFFB\u200D♂️ 궁금한 점이나, 추가했으면 좋겠는 기능이 있다면 봇에게 채팅으로 알려주세요.\n" +
            "\uD83D\uDC40 프로그램 코드가 궁금하다면 https://github.com/Gummybearr/Recruit_Bot_Skeleton_Code 을 방문하여 확인할 수 있어요";
}
