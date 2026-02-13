---
description: Review the merge request to ensure it aligns with the referenced Jira issue and complies with the defined technical guidelines.
allowed-tools: [ "Task", "Read", "Write", "Edit", "Bash", "Glob", "Grep", "MultiEdit", "TodoWrite", "glab" ]
---

# Implement merge request review command.

Review the merge request to ensure it aligns with the referenced Jira issue and complies with the defined technical
guidelines.

## Usage

```bash

## Review only merge request
/mr-review-only [mr-id]

## Review merge request align with related jira issue
/mr-review-only [mr-id] [jira-issue-id] 

```

## Prerequisite

- Ensure that the MCP Atlassian configuration is properly connected. (If have jira issue)
- Ensure that the GitLab API token is properly configured.

## Steps to Review

1. Retrieve merge request detail via command `glab mr view [mr-id]`.

2. Retrieve diff code in merge quest via command `glab mr diff [mr-id]`.

3. Use kotlin-dev agent to verify merge request detail match with [Merge Request Template](#merge-request-template).

4. Use system-analysis agent to retrieve jira issue detail via command `mcp atlassian getJiraIssue [jira-issue-id]` and analyze .

5. Use system-analysis agent to retrieve the related jira issue and confluence page via mcp if you have.

6. Ensure code changes implement what Jira issue and Confluence documents require.

7. Use kotlin-agent review test coverage, DB migrations, config changes, and backward compatibility (if applicable).

8. Use kotlin-agent review Spring Boot best practices and Kotlin best practices

9. Use be-security-agent review code


## Merge Request Template

```markdown
## 📋 Ticket Reference
- **Jira**: [TP1-XXXX](https://bluebik-team.atlassian.net/browse/TP1-XXXX)
- **Confluence**: [Specification Link if available]

## 🎯 Implementation Summary
[Brief description of what was implemented]

## Summary of changes
[List from changelog]

## 🔧 Technical Details
### Similar API Patterns Used:
- [Reference API/method that was followed]
- [Patterns replicated for consistency]

## 📊 Test Coverage
- **Service Layer**: XX%
- **Controller Layer**: XX%
- **Overall Coverage**: XX% (Target: >80%)

## ⚠️ Important Notes
[Any assumptions, limitations, or considerations for reviewers]

## ✅ Pre-Merge Checklist
- [ ] All tests passing
- [ ] Ktlint compliance verified
- [ ] No hard-coded values
- [ ] Proper logging implemented
- [ ] Error handling complete
- [ ] Similar API patterns followed
- [ ] Coverage >80% achieved
- [ ] Security scanning passed
```

## Review Priorities

### 🚨 Critical Issues (Must Fix - Block Merge)
<critical-issues>
- **Security vulnerabilities**: SQL injection, authentication bypasses, exposed secrets/keys, unsafe input handling, path traversal
- **Logic errors**: Incorrect business logic, race conditions, deadlocks, resource leaks, null pointer exceptions
- **Error handling**: Missing exception handling, improper error propagation, silent failures, unhandled exceptions
- **Memory safety**: Thread pool exhaustion, unbounded collections, memory leaks, infinite loops
- **Data consistency**: Improper transaction handling, concurrent access issues, data corruption risks
- **Breaking changes**: API compatibility breaks, database schema changes without migrations
</critical-issues>

### ⚠️ Major Issues (Should Fix - Consider Blocking)
<major-issues>
- **Performance problems**: Inefficient algorithms, N+1 queries, blocking operations in hot paths, missing database indexes
- **Kotlin/Spring Boot issues**: Improper coroutine usage, incorrect dependency injection, missing resource cleanup, blocking operations in reactive code
- **Code structure**: Poor separation of concerns, tight coupling, circular dependencies, overly complex functions
- **Testing gaps**: Missing tests for critical paths, inadequate error case coverage, missing edge cases
- **Configuration issues**: Missing environment validation, insecure defaults, hardcoded values
</major-issues>

### 💡 Minor Issues (Optional to Fix)
<minor-issues>
- Code style inconsistencies (if not caught by linters)
- Minor refactoring opportunities
- Documentation improvements
- Variable naming improvements
- Non-critical logging enhancements
</minor-issues>

## Merge Request Review Template 

```markdown

# 🔍 Merge Request Review - MR#{MR_NUMBER}

**Review Date:** {YYYY-MM-DD}  
**Reviewer:** {REVIEWER_NAME}  
**Jira Issue:** [{ISSUE_KEY}]({JIRA_URL})  
**Confluence Spec:** [{SPEC_TITLE}]({CONFLUENCE_URL})

---

## {Recommendation Title}

{Brief summary of overall verdict and reasoning}

---

## 📋 1. MR Template Compliance (~{PERCENTAGE}%)

### ❌ Missing Critical Sections
- **{Section Name}**: {Description of what's missing}
- **{Section Name}**: {Description of what's missing}

### ⚠️ Needs Improvement
- **{Section Name}**: {Description of issues}
- **{Section Name}**: {Description of issues}

### ✅ Completed Sections
- **{Section Name}**: {Brief note}

**Action Required:** {What needs to be done to complete the template}

---

## 🚨 2. Critical Issues (MUST FIX)

### 🔴 **CRITICAL #{NUMBER}: {Issue Title}**
**Severity:** BLOCKING  
**Location:** `{file_path}:{line_numbers}`  
**Impact:** {Description of impact}

**Problem:**  
{Detailed description of the problem}

```{language}
// ❌ WRONG - Current implementation
{problematic_code}
```

**Required Fix:**
```{language}
// ✅ CORRECT - Proposed solution
{fixed_code}
```

**Action Required:**
1. {Step 1}
2. {Step 2}
3. {Step 3}

---

{Repeat above section for each critical issue}

---

## ⚠️ 3. Major Issues

### 🟠 **MAJOR #{NUMBER}: {Issue Title}**
**Location:** `{file_path}:{line_numbers}`

**Problem:** {Description of the problem}

**Fix:**
```{language}
// ✅ CORRECT implementation
{fixed_code}
```

**Rationale:** {Why this needs to be fixed}

---

{Repeat above section for each major issue}

---

## 🔧 4. Minor Issues & Suggestions

### 🟡 **MINOR #{NUMBER}: {Issue Title}**
**Location:** `{file_path}:{line_numbers}`

{Description and suggested fix}

---

{Repeat above section for each minor issue}

---

## 📊 5. Test Coverage Analysis

### Current Coverage
- **Controller Tests:** {number} scenarios {✅/⚠️/❌}
- **Service Tests:** {number} scenarios {✅/⚠️/❌}
- **Repository Tests:** {number} scenarios {✅/⚠️/❌}
- **Integration Tests:** {number} scenarios {✅/⚠️/❌}
- **Actual Coverage %:** {percentage}% {✅ if >80%, ⚠️ if 60-80%, ❌ if <60%}

### Required Coverage (Target: >80%)
Run coverage report:
```bash
./gradlew :{module_name}:test jacocoTestReport
```

Check: `{module_name}/build/reports/jacoco/test/html/index.html`

### Missing Test Scenarios
1. {Test scenario 1}
2. {Test scenario 2}
3. {Test scenario 3}

---

## 🎯 6. Pre-Merge Checklist

### Critical Items
- [ ] Fix Critical #1: {Brief description}
- [ ] Fix Critical #2: {Brief description}
- [ ] Fix Critical #{N}: {Brief description}

### Major Items
- [ ] Fix Major #1: {Brief description}
- [ ] Fix Major #2: {Brief description}
- [ ] Fix Major #{N}: {Brief description}

### Testing & Quality
- [ ] Add missing test scenarios
- [ ] Run and achieve >80% test coverage
- [ ] Run `./gradlew build` - all tests passing
- [ ] Performance testing completed (if applicable)

### Documentation
- [ ] Update MR description with complete template
- [ ] Update Confluence spec (if API changed)

### Code Quality
- [ ] No hard-coded values
- [ ] Proper logging implemented
- [ ] Ktlint/Detekt compliance verified
- [ ] Security scanning passed
- [ ] No code smells or anti-patterns

---

## 📝 7. Recommendations

### Immediate Actions
1. {High priority action 1}
2. {High priority action 2}
3. {High priority action 3}

### Before Next Review
1. {Action before re-review 1}
2. {Action before re-review 2}
3. {Action before re-review 3}

### Post-Merge Monitoring
1. {Monitoring item 1}
2. {Monitoring item 2}
3. {Monitoring item 3}

### Future Improvements (Optional)
1. {Nice-to-have improvement 1}
2. {Nice-to-have improvement 2}
3. 
---

## ✅ Summary

| Category | Status | Count |
|----------|--------|-------|
| Critical Issues | 🔴 | {count} |
| Major Issues | 🟠 | {count} |
| Minor Issues | 🟡 | {count} |
| Template Compliance | ⚠️ | ~{percentage}% |

**Estimated Fix Time:** {hours} hours

**Next Steps:**
1. {Next step 1}
2. {Next step 2}
3. {Next step 3}

---

## 💡 Additional Notes

{Any additional context, considerations, or information that doesn't fit elsewhere}

---

*Review conducted by {Reviewer Name/Tool}*  
*For questions, contact {contact_info} or check detailed review documents*


```