package run.app.entity.model;

import java.util.ArrayList;
import java.util.List;

public class BloggerProfileExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BloggerProfileExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andBloggerIdIsNull() {
            addCriterion("BLOGGER_ID is null");
            return (Criteria) this;
        }

        public Criteria andBloggerIdIsNotNull() {
            addCriterion("BLOGGER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andBloggerIdEqualTo(Long value) {
            addCriterion("BLOGGER_ID =", value, "bloggerId");
            return (Criteria) this;
        }

        public Criteria andBloggerIdNotEqualTo(Long value) {
            addCriterion("BLOGGER_ID <>", value, "bloggerId");
            return (Criteria) this;
        }

        public Criteria andBloggerIdGreaterThan(Long value) {
            addCriterion("BLOGGER_ID >", value, "bloggerId");
            return (Criteria) this;
        }

        public Criteria andBloggerIdGreaterThanOrEqualTo(Long value) {
            addCriterion("BLOGGER_ID >=", value, "bloggerId");
            return (Criteria) this;
        }

        public Criteria andBloggerIdLessThan(Long value) {
            addCriterion("BLOGGER_ID <", value, "bloggerId");
            return (Criteria) this;
        }

        public Criteria andBloggerIdLessThanOrEqualTo(Long value) {
            addCriterion("BLOGGER_ID <=", value, "bloggerId");
            return (Criteria) this;
        }

        public Criteria andBloggerIdIn(List<Long> values) {
            addCriterion("BLOGGER_ID in", values, "bloggerId");
            return (Criteria) this;
        }

        public Criteria andBloggerIdNotIn(List<Long> values) {
            addCriterion("BLOGGER_ID not in", values, "bloggerId");
            return (Criteria) this;
        }

        public Criteria andBloggerIdBetween(Long value1, Long value2) {
            addCriterion("BLOGGER_ID between", value1, value2, "bloggerId");
            return (Criteria) this;
        }

        public Criteria andBloggerIdNotBetween(Long value1, Long value2) {
            addCriterion("BLOGGER_ID not between", value1, value2, "bloggerId");
            return (Criteria) this;
        }

        public Criteria andAboutMeIsNull() {
            addCriterion("ABOUT_ME is null");
            return (Criteria) this;
        }

        public Criteria andAboutMeIsNotNull() {
            addCriterion("ABOUT_ME is not null");
            return (Criteria) this;
        }

        public Criteria andAboutMeEqualTo(String value) {
            addCriterion("ABOUT_ME =", value, "aboutMe");
            return (Criteria) this;
        }

        public Criteria andAboutMeNotEqualTo(String value) {
            addCriterion("ABOUT_ME <>", value, "aboutMe");
            return (Criteria) this;
        }

        public Criteria andAboutMeGreaterThan(String value) {
            addCriterion("ABOUT_ME >", value, "aboutMe");
            return (Criteria) this;
        }

        public Criteria andAboutMeGreaterThanOrEqualTo(String value) {
            addCriterion("ABOUT_ME >=", value, "aboutMe");
            return (Criteria) this;
        }

        public Criteria andAboutMeLessThan(String value) {
            addCriterion("ABOUT_ME <", value, "aboutMe");
            return (Criteria) this;
        }

        public Criteria andAboutMeLessThanOrEqualTo(String value) {
            addCriterion("ABOUT_ME <=", value, "aboutMe");
            return (Criteria) this;
        }

        public Criteria andAboutMeLike(String value) {
            addCriterion("ABOUT_ME like", value, "aboutMe");
            return (Criteria) this;
        }

        public Criteria andAboutMeNotLike(String value) {
            addCriterion("ABOUT_ME not like", value, "aboutMe");
            return (Criteria) this;
        }

        public Criteria andAboutMeIn(List<String> values) {
            addCriterion("ABOUT_ME in", values, "aboutMe");
            return (Criteria) this;
        }

        public Criteria andAboutMeNotIn(List<String> values) {
            addCriterion("ABOUT_ME not in", values, "aboutMe");
            return (Criteria) this;
        }

        public Criteria andAboutMeBetween(String value1, String value2) {
            addCriterion("ABOUT_ME between", value1, value2, "aboutMe");
            return (Criteria) this;
        }

        public Criteria andAboutMeNotBetween(String value1, String value2) {
            addCriterion("ABOUT_ME not between", value1, value2, "aboutMe");
            return (Criteria) this;
        }

        public Criteria andNicknameIsNull() {
            addCriterion("NICKNAME is null");
            return (Criteria) this;
        }

        public Criteria andNicknameIsNotNull() {
            addCriterion("NICKNAME is not null");
            return (Criteria) this;
        }

        public Criteria andNicknameEqualTo(String value) {
            addCriterion("NICKNAME =", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameNotEqualTo(String value) {
            addCriterion("NICKNAME <>", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameGreaterThan(String value) {
            addCriterion("NICKNAME >", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameGreaterThanOrEqualTo(String value) {
            addCriterion("NICKNAME >=", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameLessThan(String value) {
            addCriterion("NICKNAME <", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameLessThanOrEqualTo(String value) {
            addCriterion("NICKNAME <=", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameLike(String value) {
            addCriterion("NICKNAME like", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameNotLike(String value) {
            addCriterion("NICKNAME not like", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameIn(List<String> values) {
            addCriterion("NICKNAME in", values, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameNotIn(List<String> values) {
            addCriterion("NICKNAME not in", values, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameBetween(String value1, String value2) {
            addCriterion("NICKNAME between", value1, value2, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameNotBetween(String value1, String value2) {
            addCriterion("NICKNAME not between", value1, value2, "nickname");
            return (Criteria) this;
        }

        public Criteria andAvatarIdIsNull() {
            addCriterion("AVATAR_ID is null");
            return (Criteria) this;
        }

        public Criteria andAvatarIdIsNotNull() {
            addCriterion("AVATAR_ID is not null");
            return (Criteria) this;
        }

        public Criteria andAvatarIdEqualTo(Long value) {
            addCriterion("AVATAR_ID =", value, "avatarId");
            return (Criteria) this;
        }

        public Criteria andAvatarIdNotEqualTo(Long value) {
            addCriterion("AVATAR_ID <>", value, "avatarId");
            return (Criteria) this;
        }

        public Criteria andAvatarIdGreaterThan(Long value) {
            addCriterion("AVATAR_ID >", value, "avatarId");
            return (Criteria) this;
        }

        public Criteria andAvatarIdGreaterThanOrEqualTo(Long value) {
            addCriterion("AVATAR_ID >=", value, "avatarId");
            return (Criteria) this;
        }

        public Criteria andAvatarIdLessThan(Long value) {
            addCriterion("AVATAR_ID <", value, "avatarId");
            return (Criteria) this;
        }

        public Criteria andAvatarIdLessThanOrEqualTo(Long value) {
            addCriterion("AVATAR_ID <=", value, "avatarId");
            return (Criteria) this;
        }

        public Criteria andAvatarIdIn(List<Long> values) {
            addCriterion("AVATAR_ID in", values, "avatarId");
            return (Criteria) this;
        }

        public Criteria andAvatarIdNotIn(List<Long> values) {
            addCriterion("AVATAR_ID not in", values, "avatarId");
            return (Criteria) this;
        }

        public Criteria andAvatarIdBetween(Long value1, Long value2) {
            addCriterion("AVATAR_ID between", value1, value2, "avatarId");
            return (Criteria) this;
        }

        public Criteria andAvatarIdNotBetween(Long value1, Long value2) {
            addCriterion("AVATAR_ID not between", value1, value2, "avatarId");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}