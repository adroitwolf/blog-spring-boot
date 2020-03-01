package run.app.entity.VO;

import lombok.*;
import org.apache.commons.lang3.StringUtils;
import run.app.entity.enums.SortOrderEnum;

import javax.validation.constraints.Min;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2020 2020/1/30 17:14
 * Description: 用来表示页面的实体类
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PageInfo {

    @NonNull
    @Min(value=0,message = "请不要进行非法操作")
    int pageSize;

    @NonNull
    @Min(value=0,message = "请不要进行非法操作")
    int pageNum;

    @NonNull
    String sortName;

    @NonNull
    String sortOrder;


    public String convertToSortSql(){

        StringBuilder builder = new StringBuilder();

        builder.append(StringUtils.isEmpty(sortName) ?null:sortName);
        builder.append(" ");
        builder.append(StringUtils.isEmpty(sortOrder)?null:SortOrderEnum.valueOf(sortOrder));
        return StringUtils.isEmpty(sortName)?null:builder.toString();
    }

}
