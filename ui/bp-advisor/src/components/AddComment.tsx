import React, { useState } from 'react';
import styles from './AddComment.module.css';

interface AddCommentProps {
  onSubmit: (comment: string) => void;
}

const AddComment: React.FC<AddCommentProps> = ({ onSubmit }) => {
  const [comment, setComment] = useState('');

  const handleChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
    setComment(event.target.value);
  };

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    onSubmit(comment);
    setComment('');
  };

  const isCommentEmpty = comment.trim().length === 0;

  return (
    <form className={styles.addComment} onSubmit={handleSubmit}>
      <label>
        Add a comment:
        <textarea className={styles.commentTextarea} value={comment} onChange={handleChange} />
      </label>
      <button
        className={styles.submitButton}
        type="submit"
        disabled={isCommentEmpty}
        style={{ backgroundColor: isCommentEmpty ? '#ccc' : undefined }}
      >
        Submit
      </button>
    </form>
  );
};

export default AddComment;
