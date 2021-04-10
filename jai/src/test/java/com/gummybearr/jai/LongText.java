package com.gummybearr.jai;

public class LongText {
    public static final String longText = generateFor100Times();

    private static String generateFor100Times(){
        StringBuilder stringBuilder = new StringBuilder();

        //학교 사이트에서는 top 50개를 긁고
        //잡코리아에서는 top 30개만 긁어주도록 하자
        for(int count = 0;count<80;count++){
            stringBuilder.append("하나머티리얼즈(주)\n");
            stringBuilder.append("하나머티리얼즈 각 부분 신입/경력 모집\n");
            stringBuilder.append("Due: ~04/09(금)\n");
            stringBuilder.append("\n");
        }
        return String.valueOf(stringBuilder);
    }
}
